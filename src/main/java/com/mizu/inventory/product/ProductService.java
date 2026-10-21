package com.mizu.inventory.product;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mizu.inventory.product.dto.ProductRequest;
import com.mizu.inventory.product.dto.ProductResponse;

public interface ProductService {
  ProductResponse createProduct(ProductRequest dto);

  Page<ProductResponse> getAllProducts(Pageable pageable);

  Page<ProductResponse> getLowStockProducts(Pageable pageable);

  ProductResponse getProductById(UUID id);

  ProductResponse getProductBySku(String sku);

  ProductResponse updateProduct(UUID id, ProductRequest dto);

  void removeProduct(UUID id);
}
