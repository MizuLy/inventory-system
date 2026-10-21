package com.mizu.inventory.product;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
  @Query("SELECT p FROM Product p WHERE p.quantity < p.lowStockThreshold")
  Page<Product> lowStockAlert(Pageable pageable);

  boolean existsBySku(String sku);

  Optional<Product> findBySku(String sku);
}
