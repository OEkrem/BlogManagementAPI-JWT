package com.oekrem.jwt_deneme.dtos.mappers;

import com.oekrem.jwt_deneme.dtos.requests.CreateCategoryRequest;
import com.oekrem.jwt_deneme.dtos.requests.UpdateCategoryRequest;
import com.oekrem.jwt_deneme.dtos.responses.CategoryResponse;
import com.oekrem.jwt_deneme.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CreateCategoryRequest createCategoryRequest);
    Category toCategory(UpdateCategoryRequest updateCategoryRequest);

    CategoryResponse toCategoryResponse(Category category);

}
