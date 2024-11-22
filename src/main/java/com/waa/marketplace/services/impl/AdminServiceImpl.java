package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.requests.CategoryRequestDto;
import com.waa.marketplace.dtos.responses.CategoryResponseDto;
import com.waa.marketplace.dtos.responses.ReviewResponseDto;
import com.waa.marketplace.dtos.responses.SellerResponseDto;
import com.waa.marketplace.entites.Category;
import com.waa.marketplace.entites.Review;
import com.waa.marketplace.entites.Seller;
import com.waa.marketplace.repositories.CategoryRepository;
import com.waa.marketplace.repositories.ReviewRepository;
import com.waa.marketplace.repositories.SellerRepository;
import com.waa.marketplace.services.AdminService;
import com.waa.marketplace.utils.DtoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final SellerRepository sellerRepository;
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public AdminServiceImpl(SellerRepository sellerRepository, ReviewRepository reviewRepository,
                            CategoryRepository categoryRepository) {
        this.sellerRepository = sellerRepository;
        this.reviewRepository = reviewRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<SellerResponseDto> getPendingSellers() {
        return sellerRepository.findByApprovedFalse()
                .stream()
                .map(DtoMapper::mapToSellerResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void approveSeller(Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Seller not " +
                "found"));
        seller.setApproved(true);
        sellerRepository.save(seller);
    }

    @Override
    public void rejectSeller(Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Seller not " +
                "found"));
        sellerRepository.delete(seller);
    }

    @Override
    public List<ReviewResponseDto> getReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(DtoMapper::mapToReviewResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Review not " +
                "found"));
        reviewRepository.delete(review);
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto request) {
        Optional<Category> existing = categoryRepository.findByNameIgnoreCase(request.getName());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Category name already exists");
        }
        Category category = Category.builder().name(request.getName()).description(request.getDescription()).build();
        Category savedCategory = categoryRepository.save(category);
        return DtoMapper.mapToCategoryResponseDto(savedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category " +
                "not found"));
        if (!category.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Category can not be deleted as it has products.");
        }
        categoryRepository.delete(category);
    }
}
