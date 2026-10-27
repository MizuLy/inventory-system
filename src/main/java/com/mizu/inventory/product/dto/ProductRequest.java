package com.mizu.inventory.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequest {

  @NotBlank
  @Size(max = 255)
  private String productName;

  @Size(max = 500)
  private String description;

  @NotNull
  @DecimalMin("0.00")
  private BigDecimal price;

  @NotNull
  @Min(0)
  private Integer quantity;

  @NotNull
  @Min(0)
  private Integer lowStockThreshold;

  @NotNull
  private UUID categoryId;

  private UUID supplierId;
}