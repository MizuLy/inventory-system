package com.mizu.inventory.role;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mizu.inventory.auth.AuthRepository;
import com.mizu.inventory.role.dto.RoleRequest;
import com.mizu.inventory.role.dto.RoleResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
  private final AuthRepository authRepository;

  @Override
  @Transactional
  public RoleResponse createRole(RoleRequest dto) {
    if (roleRepository.existsByName(dto.getName())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Role of that name already exists");
    }

    Role role = new Role();
    role.setName(dto.getName());

    Role saved = roleRepository.saveAndFlush(role);
    return res(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public RoleResponse getRoleById(UUID id) {
    return res(findOrThrow(id));
  }

  @Override
  @Transactional(readOnly = true)
  public Page<RoleResponse> getAllRoles(Pageable pageable) {
    return roleRepository.findAll(pageable).map(this::res);
  }

  @Override
  @Transactional
  public RoleResponse updateRole(UUID id, RoleRequest dto) {
    Role role = findOrThrow(id);

    roleRepository.findByName(dto.getName()).ifPresent(existing -> {
      if (!existing.getId().equals(id)) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Role of that name already exists");
      }
    });

    role.setName(dto.getName());

    Role saved = roleRepository.saveAndFlush(role);
    return res(saved);
  }

  @Override
  @Transactional
  public void removeRole(UUID id) {
    if (!roleRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");
    }
    if (authRepository.existsByRoleId(id)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Role is assigned to users");
    }
    roleRepository.deleteById(id);
  }

  private Role findOrThrow(UUID id) {
    return roleRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
  }

  private RoleResponse res(Role r) {
    return new RoleResponse(r.getId(), r.getName());
  }
}
