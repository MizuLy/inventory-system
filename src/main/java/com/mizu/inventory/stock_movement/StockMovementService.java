package com.mizu.inventory.stock_movement;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mizu.inventory.stock_movement.dto.StockMovementRequest;
import com.mizu.inventory.stock_movement.dto.StockMovementResponse;

public interface StockMovementService {
  StockMovementResponse createStock(UUID productId, StockMovementRequest dto);

  Page<StockMovementResponse> getAllStocks(Pageable pageable);

  StockMovementResponse getStockById(UUID id);

  // get the stock movement of a specific product
  Page<StockMovementResponse> getStockByProduct(UUID productId, Pageable pageable);
}
