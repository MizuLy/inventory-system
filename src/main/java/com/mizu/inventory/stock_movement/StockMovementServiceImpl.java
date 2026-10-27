package com.mizu.inventory.stock_movement;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mizu.inventory.auth.Auth;
import com.mizu.inventory.auth.AuthRepository;
import com.mizu.inventory.product.Product;
import com.mizu.inventory.product.ProductRepository;
import com.mizu.inventory.stock_movement.dto.StockMovementRequest;
import com.mizu.inventory.stock_movement.dto.StockMovementResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockMovementServiceImpl implements StockMovementService {
  private final ProductRepository productRepository;
  private final StockMovementRepository stockMovementRepository;
  private final AuthRepository authRepository;

  @Override
  @Transactional
  public StockMovementResponse createStock(UUID productId, StockMovementRequest dto) {
    Auth currentUser = currentUser();

    if (dto.getQuantityChange() == 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity change cannot be 0");
    }

    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

    int newQty = product.getQuantity() + dto.getQuantityChange();
    if (newQty < 0) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient stock");
    }
    product.setQuantity(newQty);

    StockMovement sm = StockMovement.builder()
        .product(product)
        .quantityChange(dto.getQuantityChange())
        .quantityAfter(newQty)
        .reason(dto.getReason())
        .descriptiveReason(dto.getDescriptiveReason())
        .createdBy(currentUser)
        .build();
    return res(stockMovementRepository.save(sm));
  }

  @Override
  @Transactional
  public void recordInitialStock(Product product) {
    int qty = product.getQuantity();
    if (qty <= 0) {
      return;
    }

    stockMovementRepository.save(StockMovement.builder()
        .product(product)
        .quantityChange(qty)
        .quantityAfter(qty)
        .reason(StockReason.ADJUSTMENT)
        .descriptiveReason("Initial stock")
        .createdBy(currentUser())
        .build());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<StockMovementResponse> getAllStocks(Pageable pageable) {
    return stockMovementRepository.findAll(pageable).map(this::res);
  }

  @Override
  @Transactional(readOnly = true)
  public StockMovementResponse getStockById(UUID id) {
    return res(findOrThrow(id));
  }

  @Override
  @Transactional(readOnly = true)
  public Page<StockMovementResponse> getStockByProduct(UUID productId, Pageable pageable) {
    if (!productRepository.existsById(productId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    return stockMovementRepository.findByProductIdOrderByCreatedAtDesc(productId, pageable).map(this::res);
  }

  private Auth currentUser() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return authRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
  }

  private StockMovement findOrThrow(UUID id) {
    return stockMovementRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Stock Movement not found"));
  }

  public StockMovementResponse res(StockMovement sm) {
    return new StockMovementResponse(
        sm.getId(),
        sm.getProduct().getId(),
        sm.getProduct().getProductName(),
        sm.getQuantityChange(),
        sm.getQuantityAfter(),
        sm.getReason(),
        sm.getDescriptiveReason(),
        sm.getCreatedBy().getId(),
        sm.getCreatedBy().getName(),
        sm.getCreatedAt());
  }
}
