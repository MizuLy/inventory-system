package com.mizu.inventory.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mizu.inventory.auth.dto.AuthResponse;
import com.mizu.inventory.auth.dto.LoginRequest;
import com.mizu.inventory.auth.dto.RegisterRequest;
import com.mizu.inventory.role.Role;
import com.mizu.inventory.role.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

  private final AuthRepository authRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  @Override
  public AuthResponse register(RegisterRequest dto) {
    if (authRepository.existsByEmail(dto.getEmail())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
    }

    // the first account ever created becomes the admin
    String roleName = authRepository.count() == 0 ? Role.ADMIN : Role.USER;
    Role role = roleRepository.findByName(roleName)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            "Role " + roleName + " is not configured"));

    Auth user = Auth.builder()
        .name(dto.getName())
        .email(dto.getEmail())
        .password(passwordEncoder.encode(dto.getPassword()))
        .role(role)
        .build();

    return res(authRepository.saveAndFlush(user));
  }

  @Override
  @Transactional(readOnly = true)
  public AuthResponse login(LoginRequest dto) {
    Auth user = authRepository.findByEmail(dto.getEmail())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    return res(user);
  }

  private AuthResponse res(Auth user) {
    return new AuthResponse(user.getId(), user.getEmail(), user.getRole(), jwtService.generateToken(user));
  }
}
