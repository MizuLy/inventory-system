package com.mizu.inventory.suppliers.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplierResponse {
  private UUID id;
  private String name;
  private String contactName;
  private String email;
  private String phone;
  private String address;
  private Boolean isActive;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
