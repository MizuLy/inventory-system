package com.mizu.inventory.suppliers;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
  List<Supplier> findByIsActiveTrueOrderByNameAsc();

  Page<Supplier> findByIsActive(Boolean isActive, Pageable pageable);
}
