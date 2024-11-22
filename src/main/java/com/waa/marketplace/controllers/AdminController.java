package com.waa.marketplace.controllers;

import com.waa.marketplace.dtos.requests.CategoryRequestDto;
import com.waa.marketplace.dtos.responses.CategoryResponseDto;
import com.waa.marketplace.dtos.responses.ReviewResponseDto;
import com.waa.marketplace.dtos.responses.SellerResponseDto;
import com.waa.marketplace.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin APIs", description = "APIs for managing sellers and reviews")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Get all pending sellers.
     * This API fetches a list of all sellers who have not yet been approved.
     *
     * @return List of SellerDto objects representing pending sellers.
     */
    @Operation(summary = "Get all pending sellers", description = "Fetches a list of sellers awaiting approval.")
    @GetMapping("/pending-sellers")
    public ResponseEntity<List<SellerResponseDto>> getAllPendingSellers() {
        List<SellerResponseDto> pendingSellers = adminService.getPendingSellers();
        return ResponseEntity.ok(pendingSellers);
    }

    /**
     * Approve a seller.
     * Approves the registration request of a specific seller by their ID.
     *
     * @param id ID of the seller to approve.
     * @return Success message indicating the seller has been approved.
     */
    @Operation(summary = "Approve a seller", description = "Approves a seller's registration request.")
    @PutMapping("/seller/{id}/approve")
    public ResponseEntity<String> approveSeller(@Parameter(description = "ID of the seller to approve",
            example = "1") @PathVariable Long id) {
        adminService.approveSeller(id);
        return ResponseEntity.ok("Seller approved successfully.");
    }

    /**
     * Reject a seller.
     * Rejects the registration request of a specific seller by their ID.
     *
     * @param id ID of the seller to reject.
     * @return Success message indicating the seller has been rejected.
     */
    @Operation(summary = "Reject a seller", description = "Rejects a seller's registration request.")
    @DeleteMapping("/seller/{id}/reject")
    public ResponseEntity<String> rejectSeller(@Parameter(description = "ID of the seller to reject", example = "1") @PathVariable Long id) {
        adminService.rejectSeller(id);
        return ResponseEntity.ok("Seller rejected successfully.");
    }

    /**
     * Get all reviews.
     * Retrieves all reviews with optional filters for product ID, rating, or buyer email.
     *
     * @return List of ReviewDto objects matching the filters.
     */
    @Operation(summary = "Get all reviews", description = "Fetches all reviews, with optional filters for product ID," +
            " rating, and buyer email.")
    @GetMapping("/review")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        List<ReviewResponseDto> reviews = adminService.getReviews();
        return ResponseEntity.ok(reviews);
    }

    /**
     * Delete a review.
     * Deletes a specific review by its unique ID.
     *
     * @param id ID of the review to delete.
     * @return Success message indicating the review has been deleted.
     */
    @Operation(summary = "Delete a review", description = "Deletes a specific review by its ID.")
    @DeleteMapping("review/{id}")
    public ResponseEntity<String> deleteReview(@Parameter(description = "ID of the review to delete",
            example = "123") @PathVariable Long id) {
        adminService.deleteReview(id);
        return ResponseEntity.ok("Review deleted successfully.");
    }

    /**
     * Adds a new category.
     *
     * @param request the category request data
     * @return the created category details
     */
    @Operation(summary = "Add a new category", description = "Creates a new category based on the provided details.")
    @PostMapping("/category")
    public ResponseEntity<CategoryResponseDto> addCategory(@RequestBody @Valid CategoryRequestDto request) {
        // Call the service layer to create the category
        CategoryResponseDto res = adminService.createCategory(request);
        return ResponseEntity.ok(res);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to delete
     * @return a message indicating successful deletion
     */
    @Operation(summary = "Delete a category", description = "Deletes the category identified by the given ID.")
    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteCategory(@Parameter(description = "ID of the category to delete", required =
            true, example = "123") @PathVariable Long id) {
        adminService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully.");
    }
}
