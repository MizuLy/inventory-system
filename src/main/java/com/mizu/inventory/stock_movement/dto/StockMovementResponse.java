package com.mizu.inventory.stock_movement.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mizu.inventory.stock_movement.StockReason;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockMovementResponse {
  private UUID id;
  private UUID productId;
  private String productName;
  private Integer quantityChange;
  private Integer quantityAfter;
  private StockReason reason;
  private String descriptiveReason;
  private UUID createdBy;
  private String name;
  private LocalDateTime createdAt;
}
