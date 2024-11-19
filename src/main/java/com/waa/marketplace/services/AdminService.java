package com.waa.marketplace.services;

import com.waa.marketplace.dtos.responses.ReviewResponseDto;
import com.waa.marketplace.dtos.responses.SellerResponseDto;

import java.util.List;

public interface AdminService {
    List<SellerResponseDto> getPendingSellers();

    void approveSeller(Long id);

    void rejectSeller(Long id);

    List<ReviewResponseDto> getReviews(Long productId, Integer rating, String buyerEmail);

    void deleteReview(Long id);
}
