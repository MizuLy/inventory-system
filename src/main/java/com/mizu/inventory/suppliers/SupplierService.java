package com.mizu.inventory.suppliers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mizu.inventory.suppliers.dto.SupplierRequest;
import com.mizu.inventory.suppliers.dto.SupplierResponse;

public interface SupplierService {
  SupplierResponse createSupplier(SupplierRequest dto);

  Page<SupplierResponse> getAllSuppliers(Pageable pageable);

  SupplierResponse getSupplierById(UUID id);

  SupplierResponse updateSupplier(UUID id, SupplierRequest dto);

  void removeSupplier(UUID id);
}
