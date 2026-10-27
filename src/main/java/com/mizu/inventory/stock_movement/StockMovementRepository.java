package com.mizu.inventory.stock_movement;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> {
  Page<StockMovement> findByProductIdOrderByCreatedAtDesc(UUID productId, Pageable pageable);
}
