package com.mizu.inventory.role;

import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mizu.inventory.role.dto.RoleRequest;
import com.mizu.inventory.role.dto.RoleResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Role")
public class RoleController {
  private final RoleService roleService;

  @PostMapping
  public RoleResponse createRole(@Valid @RequestBody RoleRequest dto) {
    return roleService.createRole(dto);
  }

  @GetMapping("/{id}")
  public RoleResponse getRoleById(@PathVariable UUID id) {
    return roleService.getRoleById(id);
  }

  @GetMapping
  public Page<RoleResponse> getAllRoles(
      @ParameterObject @PageableDefault(size = 10, sort = "name") Pageable pageable) {
    return roleService.getAllRoles(pageable);
  }

  @PutMapping("/{id}")
  public RoleResponse updateRole(@PathVariable UUID id, @Valid @RequestBody RoleRequest dto) {
    return roleService.updateRole(id, dto);
  }

  @DeleteMapping("/{id}")
  public void removeRole(@PathVariable UUID id) {
    roleService.removeRole(id);
  }
}
