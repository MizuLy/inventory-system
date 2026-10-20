package com.mizu.inventory.category;

import java.util.UUID;

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

import com.mizu.inventory.category.dto.CategoryRequest;
import com.mizu.inventory.category.dto.CategoryResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {
  private final CategoryService categoryService;

  @PostMapping
  public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest dto) {
    return categoryService.createCategory(dto);
  }

  @GetMapping("/{id}")
  public CategoryResponse getCategoryById(@PathVariable UUID id) {
    return categoryService.getCategoryById(id);
  }

  @GetMapping
  public Page<CategoryResponse> getAllCategories(
      @ParameterObject @PageableDefault(size = 10, sort = "name") Pageable pageable) {
    return categoryService.getAllCategories(pageable);
  }

  @PutMapping("/{id}")
  public CategoryResponse updateCategory(@PathVariable UUID id, @Valid @RequestBody CategoryRequest dto) {
    return categoryService.updateCategory(id, dto);
  }

  @DeleteMapping("/{id}")
  public void removeCategory(@PathVariable UUID id) {
    categoryService.removeCategory(id);
  }
}
