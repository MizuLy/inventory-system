package com.mizu.inventory.auth;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Auth role test")
public class RoleTestController {

  @GetMapping("/me")
  public Map<String, Object> me(Authentication authentication) {
    List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    return Map.of("email", authentication.getName(), "roles", roles);
  }

  @GetMapping("/user")
  public String user() {
    return "OK: any logged-in user";
  }

  @GetMapping("/admin")
  public String admin() {
    return "OK: ADMIN only";
  }
}
