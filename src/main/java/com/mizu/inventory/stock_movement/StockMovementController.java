package com.mizu.inventory.stock_movement;

import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mizu.inventory.stock_movement.dto.StockMovementRequest;
import com.mizu.inventory.stock_movement.dto.StockMovementResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stock-movements")
@Tag(name = "Stock Movement")
@RequiredArgsConstructor
public class StockMovementController {
  private final StockMovementService stockMovementService;

  @PostMapping("/p/{productId}")
  public StockMovementResponse createStock(@PathVariable UUID productId, @Valid @RequestBody StockMovementRequest dto) {
    return stockMovementService.createStock(productId, dto);
  }

  @GetMapping
  public Page<StockMovementResponse> getAllStocks(
      @ParameterObject @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    return stockMovementService.getAllStocks(pageable);
  }

  @GetMapping("/{id}")
  public StockMovementResponse getStockById(@PathVariable UUID id) {
    return stockMovementService.getStockById(id);
  }

  @GetMapping("/p/{productId}")
  public Page<StockMovementResponse> getStockByProduct(@PathVariable UUID productId,
      @ParameterObject @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    return stockMovementService.getStockByProduct(productId, pageable);
  }
}
