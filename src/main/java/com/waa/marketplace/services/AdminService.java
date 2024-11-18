package com.waa.marketplace.services;

import com.waa.marketplace.dtos.ReviewDto;
import com.waa.marketplace.dtos.SellerDto;

import java.util.List;

public interface AdminService {
    List<SellerDto> getPendingSellers();

    void approveSeller(Long id);

    void rejectSeller(Long id);

    List<ReviewDto> getReviews(Long productId, Integer rating, String buyerEmail);

    void deleteReview(Long id);
}
