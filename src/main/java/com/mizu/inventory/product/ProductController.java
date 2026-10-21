package com.mizu.inventory.product;

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

import com.mizu.inventory.product.dto.ProductRequest;
import com.mizu.inventory.product.dto.ProductResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products")
public class ProductController {
  private final ProductService productService;

  @PostMapping
  public ProductResponse createProduct(@Valid @RequestBody ProductRequest dto) {
    return productService.createProduct(dto);
  }

  @GetMapping
  public Page<ProductResponse> getAllProducts(
      @ParameterObject @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
    return productService.getAllProducts(pageable);
  }

  @GetMapping("/low-stock")
  public Page<ProductResponse> getLowStockProduct(
      @ParameterObject @PageableDefault(size = 10, sort = "quantity") Pageable pageable) {
    return productService.getLowStockProducts(pageable);
  }

  @GetMapping("/{id}")
  public ProductResponse getProductById(@PathVariable UUID id) {
    return productService.getProductById(id);
  }

  @GetMapping("/sku/{sku}")
  public ProductResponse getProductBySku(@PathVariable String sku) {
    return productService.getProductBySku(sku);
  }

  @PutMapping("/{id}")
  public ProductResponse updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductRequest dto) {
    return productService.updateProduct(id, dto);
  }

  @DeleteMapping("/{id}")
  public void removeProduct(@PathVariable UUID id) {
    productService.removeProduct(id);
  }
}
