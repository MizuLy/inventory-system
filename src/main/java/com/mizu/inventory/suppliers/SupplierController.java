package com.mizu.inventory.suppliers;

import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mizu.inventory.suppliers.dto.SupplierRequest;
import com.mizu.inventory.suppliers.dto.SupplierResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Tag(name = "Suppliers")
public class SupplierController {
  private final SupplierService supplierService;

  @PostMapping
  public SupplierResponse createSupplier(@Valid @RequestBody SupplierRequest dto) {
    return supplierService.createSupplier(dto);
  }

  @GetMapping
  public Page<SupplierResponse> getAllSuppliers(
      @ParameterObject @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    return supplierService.getAllSuppliers(pageable);
  }

  @GetMapping("/{id}")
  public SupplierResponse getSupplierById(@PathVariable UUID id) {
    return supplierService.getSupplierById(id);
  }

  @PutMapping("/{id}")
  public SupplierResponse updateSupplier(@PathVariable UUID id, @Valid @RequestBody SupplierRequest dto) {
    return supplierService.updateSupplier(id, dto);
  }

  @DeleteMapping("/{id}")
  public void removeSupplier(@PathVariable UUID id) {
    supplierService.removeSupplier(id);
  }
}
