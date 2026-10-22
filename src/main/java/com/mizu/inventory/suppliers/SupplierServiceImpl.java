package com.mizu.inventory.suppliers;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mizu.inventory.product.ProductRepository;
import com.mizu.inventory.suppliers.dto.SupplierRequest;
import com.mizu.inventory.suppliers.dto.SupplierResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
  private final SupplierRepository supplierRepository;
  private final ProductRepository productRepository;

  @Override
  @Transactional
  public SupplierResponse createSupplier(SupplierRequest dto) {
    Supplier supplier = new Supplier();
    supplier.setName(dto.getName());
    supplier.setContactName(dto.getContactName());
    supplier.setEmail(dto.getEmail());
    supplier.setPhone(dto.getPhone());
    supplier.setAddress(dto.getAddress());

    Supplier saved = supplierRepository.saveAndFlush(supplier);
    return res(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<SupplierResponse> getAllSuppliers(Pageable pageable) {
    return supplierRepository.findAll(pageable).map(this::res);
  }

  @Override
  @Transactional(readOnly = true)
  public SupplierResponse getSupplierById(UUID id) {
    return res(findOrThrow(id));
  }

  @Override
  @Transactional
  public SupplierResponse updateSupplier(UUID id, SupplierRequest dto) {
    Supplier supplier = findOrThrow(id);

    supplier.setName(dto.getName());
    supplier.setContactName(dto.getContactName());
    supplier.setEmail(dto.getEmail());
    supplier.setPhone(dto.getPhone());
    supplier.setAddress(dto.getAddress());
    if (dto.getIsActive() != null) {
      supplier.setIsActive(dto.getIsActive());
    }

    Supplier saved = supplierRepository.saveAndFlush(supplier);
    return res(saved);
  }

  @Override
  @Transactional
  public void removeSupplier(UUID id) {
    if (!supplierRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found");
    }
    if (productRepository.existsBySupplierId(id)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Supplier is used by products. Deactivate it instead.");
    }
    supplierRepository.deleteById(id);
  }

  private Supplier findOrThrow(UUID id) {
    return supplierRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));
  }

  private SupplierResponse res(Supplier s) {
    return new SupplierResponse(
        s.getId(),
        s.getName(),
        s.getContactName(),
        s.getEmail(),
        s.getPhone(),
        s.getAddress(),
        s.getIsActive(),
        s.getCreatedAt(),
        s.getUpdatedAt());
  }
}
