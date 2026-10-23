package com.mizu.inventory.auth.dto;

import java.util.UUID;

import com.mizu.inventory.role.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
  private UUID id;
  private String email;
  private Role role;
  private String token;
}
