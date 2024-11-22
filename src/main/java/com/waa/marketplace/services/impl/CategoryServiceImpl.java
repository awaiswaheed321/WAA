package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.responses.CategoryResponseDto;
import com.waa.marketplace.entites.Category;
import com.waa.marketplace.repositories.CategoryRepository;
import com.waa.marketplace.services.CategoryService;
import com.waa.marketplace.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.isEmpty() ? List.of() : categories.stream().map(DtoMapper::mapToCategoryResponseDto).toList();
    }
}
