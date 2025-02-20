package com.oekrem.jwt_deneme.services;

import com.oekrem.jwt_deneme.dtos.requests.CreateCategoryRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdateCategoryRequest;
import com.oekrem.jwt_deneme.dtos.responses.CategoryResponse;
import com.oekrem.jwt_deneme.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryResponse> getAll();
    CategoryResponse getById(UUID id);
    CategoryResponse save(CreateCategoryRequest createCategoryRequest);
    void delete(UUID id);
    CategoryResponse update(UUID id, UpdateCategoryRequest updateCategoryRequest);

    Category validateCtegoryById(UUID id);
    Category validateCategoryByName(String name);

}
