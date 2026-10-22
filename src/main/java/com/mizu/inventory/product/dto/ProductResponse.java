package com.mizu.inventory.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponse {
  private UUID id;
  private String sku;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer quantity;
  private Integer lowStockThreshold;
  private String categoryName;
  private String supplierName;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
