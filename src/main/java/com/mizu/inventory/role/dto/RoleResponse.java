package com.mizu.inventory.role.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleResponse {
  private UUID id;
  private String name;
}
