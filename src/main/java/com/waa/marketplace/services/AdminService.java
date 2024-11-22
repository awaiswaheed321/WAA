package com.waa.marketplace.services;

import com.waa.marketplace.dtos.requests.CategoryRequestDto;
import com.waa.marketplace.dtos.responses.CategoryResponseDto;
import com.waa.marketplace.dtos.responses.ReviewResponseDto;
import com.waa.marketplace.dtos.responses.SellerResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface AdminService {
    List<SellerResponseDto> getPendingSellers();

    void approveSeller(Long id);

    void rejectSeller(Long id);

    List<ReviewResponseDto> getReviews();

    void deleteReview(Long id);

    CategoryResponseDto createCategory(@Valid CategoryRequestDto request);

    void deleteCategory(Long id);
}
