package com.oekrem.jwt_deneme.services.impl;

import com.oekrem.jwt_deneme.dtos.mappers.CategoryMapper;
import com.oekrem.jwt_deneme.dtos.requests.CreateCategoryRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdateCategoryRequest;
import com.oekrem.jwt_deneme.dtos.responses.CategoryResponse;
import com.oekrem.jwt_deneme.exceptions.CategoryExceptions.CategoryNameAlreadyTaken;
import com.oekrem.jwt_deneme.models.Category;
import com.oekrem.jwt_deneme.repositories.CategoryRepository;
import com.oekrem.jwt_deneme.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    @Transactional
    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::toCategoryResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryResponse getById(UUID id) {
        Category category = validateCtegoryById(id);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    @Transactional
    public CategoryResponse save(CreateCategoryRequest createCategoryRequest) {
        categoryRepository.findByName(createCategoryRequest.getName())
                        .ifPresent(existCategory -> { throw new CategoryNameAlreadyTaken("Category name already exists");});

        Category category = categoryMapper.toCategory(createCategoryRequest);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CategoryResponse update(UUID id, UpdateCategoryRequest updateCategoryRequest) {
        validateCtegoryById(id);
        Category category = categoryMapper.toCategory(updateCategoryRequest);
        category.setId(id);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(savedCategory);
    }

    @Override
    public Category validateCtegoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Category with id " + id + " not found"));
    }

    @Override
    public Category validateCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow( ()-> new EntityNotFoundException("Category with name " + name + " not found"));
    }
}
