package com.mizu.inventory.stock_movement.dto;

import com.mizu.inventory.stock_movement.StockReason;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StockMovementRequest {
  @NotNull
  private Integer quantityChange = 0;

  @NotNull
  private StockReason reason;

  @Size(max = 500)
  private String descriptiveReason;
}
