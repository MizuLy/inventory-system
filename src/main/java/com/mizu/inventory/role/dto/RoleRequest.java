package com.mizu.inventory.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleRequest {
  @NotBlank
  @Size(max = 50)
  private String name;
}
