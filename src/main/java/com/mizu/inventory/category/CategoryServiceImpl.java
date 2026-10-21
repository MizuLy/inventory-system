package com.mizu.inventory.category;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.mizu.inventory.category.dto.CategoryRequest;
import com.mizu.inventory.category.dto.CategoryResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;

  @Override
  @Transactional
  public CategoryResponse createCategory(CategoryRequest dto) {
    if (categoryRepository.existsByName(dto.getName())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Category of that name already exists");
    }

    Category category = new Category();
    category.setName(dto.getName());

    Category saved = categoryRepository.saveAndFlush(category);
    return res(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public CategoryResponse getCategoryById(UUID id) {
    return res(findOrThrow(id));
  }

  @Override
  @Transactional(readOnly = true)
  public Page<CategoryResponse> getAllCategories(Pageable pageable) {
    return categoryRepository.findAll(pageable).map(this::res);
  }

  @Override
  @Transactional
  public CategoryResponse updateCategory(UUID id, CategoryRequest dto) {
    Category category = findOrThrow(id);

    categoryRepository.findByName(dto.getName()).ifPresent(existing -> {
      if (!existing.getId().equals(id)) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Category of that name already exists");
      }
    });

    category.setName(dto.getName());

    Category saved = categoryRepository.saveAndFlush(category);
    return res(saved);
  }

  @Override
  @Transactional
  public void removeCategory(UUID id) {
    if (!categoryRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
    }
    categoryRepository.deleteById(id);
  }

  private Category findOrThrow(UUID id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
  }

  private CategoryResponse res(Category c) {
    return new CategoryResponse(c.getId(), c.getName(), c.getCreatedAt(), c.getUpdatedAt());
  }
}
