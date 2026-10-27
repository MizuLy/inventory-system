package com.mizu.inventory.product;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mizu.inventory.category.Category;
import com.mizu.inventory.category.CategoryRepository;
import com.mizu.inventory.product.dto.ProductRequest;
import com.mizu.inventory.product.dto.ProductResponse;
import com.mizu.inventory.suppliers.Supplier;
import com.mizu.inventory.suppliers.SupplierRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final SupplierRepository supplierRepository;

  @Override
  @Transactional
  public ProductResponse createProduct(ProductRequest dto) {
    Category category = categoryRepository.findById(dto.getCategoryId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

    Supplier supplier = null;

    if (dto.getSupplierId() != null) {
      supplier = supplierRepository.findById(dto.getSupplierId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

      if (!supplier.getIsActive()) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Supplier is inactive");
      }
    }

    String sku;

    do {
      sku = "KS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    } while (productRepository.existsBySku(sku));

    Product product = new Product();
    product.setSku(sku);
    product.setProductName(dto.getProductName());
    product.setDescription(dto.getDescription());
    product.setPrice(dto.getPrice());
    product.setQuantity(dto.getQuantity());
    product.setLowStockThreshold(dto.getLowStockThreshold());
    product.setCategory(category);
    product.setSupplier(supplier);

    Product saved = productRepository.saveAndFlush(product);
    return res(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> getAllProducts(Pageable pageable) {
    return productRepository.findAll(pageable).map(this::res);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> getAllProductsBySupplier(Pageable pageable, UUID supplierId) {
    if (!supplierRepository.existsById(supplierId)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found");
    }

    return productRepository.findBySupplierId(supplierId, pageable).map(this::res);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ProductResponse> getLowStockProducts(Pageable pageable) {
    return productRepository.lowStockAlert(pageable).map(this::res);
  }

  @Override
  @Transactional(readOnly = true)
  public ProductResponse getProductById(UUID id) {
    return res(findOrThrow(id));
  }

  @Override
  @Transactional(readOnly = true)
  public ProductResponse getProductBySku(String sku) {
    Product product = productRepository.findBySku(sku)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with that sku not found"));

    return res(product);
  }

  @Override
  @Transactional
  public ProductResponse updateProduct(UUID id, ProductRequest dto) {
    Product product = findOrThrow(id);

    Category category = categoryRepository.findById(dto.getCategoryId())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

    Supplier supplier = null;

    if (dto.getSupplierId() != null) {
      supplier = supplierRepository.findById(dto.getSupplierId())
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

      // Only reject an inactive supplier when it's being newly assigned, so products
      // whose supplier was deactivated later can still be edited.
      boolean changed = product.getSupplier() == null
          || !product.getSupplier().getId().equals(supplier.getId());
      if (changed && !supplier.getIsActive()) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Supplier is inactive");
      }
    }

    product.setProductName(dto.getProductName());
    product.setDescription(dto.getDescription());
    product.setPrice(dto.getPrice());
    product.setQuantity(dto.getQuantity());
    product.setLowStockThreshold(dto.getLowStockThreshold());
    product.setCategory(category);
    product.setSupplier(supplier);

    Product saved = productRepository.saveAndFlush(product);
    return res(saved);
  }

  @Override
  @Transactional
  public void removeProduct(UUID id) {
    if (!productRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }
    productRepository.deleteById(id);
  }

  private Product findOrThrow(UUID id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
  }

  private ProductResponse res(Product p) {
    return new ProductResponse(
        p.getId(),
        p.getSku(),
        p.getProductName(),
        p.getDescription(),
        p.getPrice(),
        p.getQuantity(),
        p.getLowStockThreshold(),
        p.getCategory().getName(),
        p.getSupplier() != null ? p.getSupplier().getName() : null,
        p.getCreatedAt(),
        p.getUpdatedAt());
  }
}
