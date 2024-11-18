package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.ReviewDto;
import com.waa.marketplace.dtos.SellerDto;
import com.waa.marketplace.entites.Review;
import com.waa.marketplace.entites.Seller;
import com.waa.marketplace.repositories.ReviewRepository;
import com.waa.marketplace.repositories.SellerRepository;
import com.waa.marketplace.services.AdminService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private final SellerRepository sellerRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public AdminServiceImpl(SellerRepository sellerRepository, ReviewRepository reviewRepository) {
        this.sellerRepository = sellerRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<SellerDto> getPendingSellers() {
        return sellerRepository.findByApprovedFalse()
                .stream()
                .map(seller -> new SellerDto(seller.getId(), seller.getUser().getName(), seller.getUser().getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public void approveSeller(Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Seller not found"));
        seller.setApproved(true);
        sellerRepository.save(seller);
    }

    @Override
    public void rejectSeller(Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Seller not found"));
        sellerRepository.delete(seller);
    }

    @Override
    public List<ReviewDto> getReviews(Long productId, Integer rating, String buyerEmail) {
        return reviewRepository.findReviewsWithFilters(productId, rating, buyerEmail)
                .stream()
                .map(review -> new ReviewDto(review.getId(), review.getProduct().getName(), review.getRating(), review.getComment()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Review not found"));
        reviewRepository.delete(review);
    }
}
