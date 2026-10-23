package com.mizu.inventory.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mizu.inventory.auth.dto.AuthResponse;
import com.mizu.inventory.auth.dto.LoginRequest;
import com.mizu.inventory.auth.dto.RegisterRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public AuthResponse register(@Valid @RequestBody RegisterRequest dto) {
    return authService.register(dto);
  }

  @PostMapping("/login")
  public AuthResponse login(@Valid @RequestBody LoginRequest dto) {
    return authService.login(dto);
  }
}
