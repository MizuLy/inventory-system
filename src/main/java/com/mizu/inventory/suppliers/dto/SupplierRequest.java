package com.mizu.inventory.suppliers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierRequest {
  @NotBlank
  @Size(max = 100)
  private String name;

  @Size(max = 100)
  private String contactName;

  @Email
  @Size(max = 255)
  private String email;

  @Size(max = 20)
  private String phone;

  @Size(max = 500)
  private String address;

  private Boolean isActive;
}
