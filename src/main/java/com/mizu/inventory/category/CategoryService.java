package com.mizu.inventory.category;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mizu.inventory.category.dto.CategoryRequest;
import com.mizu.inventory.category.dto.CategoryResponse;

public interface CategoryService {
  CategoryResponse createCategory(CategoryRequest dto);

  Page<CategoryResponse> getAllCategories(Pageable pageable);

  CategoryResponse getCategoryById(UUID id);

  CategoryResponse updateCategory(UUID id, CategoryRequest dto);

  void removeCategory(UUID id);
}
