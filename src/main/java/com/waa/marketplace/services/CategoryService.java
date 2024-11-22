package com.waa.marketplace.services;

import com.waa.marketplace.dtos.responses.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> getAllCategories();
}
