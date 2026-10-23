package com.mizu.inventory.auth;

import com.mizu.inventory.auth.dto.AuthResponse;
import com.mizu.inventory.auth.dto.LoginRequest;
import com.mizu.inventory.auth.dto.RegisterRequest;

public interface AuthService {
  AuthResponse register(RegisterRequest dto);

  AuthResponse login(LoginRequest dto);
}