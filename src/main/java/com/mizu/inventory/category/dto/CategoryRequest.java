package com.mizu.inventory.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {
  @NotBlank
  @Size(max = 50)
  private String name;
}
