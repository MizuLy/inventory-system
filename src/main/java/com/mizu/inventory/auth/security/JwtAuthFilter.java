package com.mizu.inventory.auth.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mizu.inventory.auth.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private static final String BEARER = "Bearer ";

  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (header != null && header.startsWith(BEARER)
        && SecurityContextHolder.getContext().getAuthentication() == null) {
      try {
        Claims claims = jwtService.parse(header.substring(BEARER.length()));
        String role = claims.get("role", String.class);
        List<SimpleGrantedAuthority> authorities = role == null
            ? List.of()
            : List.of(new SimpleGrantedAuthority("ROLE_" + role));

        SecurityContextHolder.getContext()
            .setAuthentication(new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities));
      } catch (JwtException | IllegalArgumentException e) {
        SecurityContextHolder.clearContext();
      }
    }

    chain.doFilter(request, response);
  }
}
