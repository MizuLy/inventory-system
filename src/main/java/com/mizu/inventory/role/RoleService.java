package com.mizu.inventory.role;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mizu.inventory.role.dto.RoleRequest;
import com.mizu.inventory.role.dto.RoleResponse;

public interface RoleService {
  RoleResponse createRole(RoleRequest dto);

  Page<RoleResponse> getAllRoles(Pageable pageable);

  RoleResponse getRoleById(UUID id);

  RoleResponse updateRole(UUID id, RoleRequest dto);

  void removeRole(UUID id);
}
