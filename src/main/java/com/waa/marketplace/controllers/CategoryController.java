package com.waa.marketplace.controllers;

import com.waa.marketplace.dtos.responses.CategoryResponseDto;
import com.waa.marketplace.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for managing categories.
 */
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Retrieves all categories.
     *
     * @return a list of all categories
     */
    @Operation(summary = "Get all categories", description = "Retrieves a list of all categories in the system.")
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        // Call service layer to fetch all categories
        List<CategoryResponseDto> res = categoryService.getAllCategories();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
