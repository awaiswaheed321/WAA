package com.waa.marketplace.controllers;

import com.waa.marketplace.dtos.requests.BuyerSignupDto;
import com.waa.marketplace.dtos.requests.SellerSignupDto;
import com.waa.marketplace.dtos.responses.BuyerResponseDto;
import com.waa.marketplace.dtos.responses.SellerResponseDto;
import com.waa.marketplace.services.SignupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/signup")
@Tag(name = "Signup APIs", description = "APIs for new seller/buyer to sign up")
public class SignupController {
    private final SignupService signupService;

    @Autowired
    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping("/seller")
    @Operation(
            summary = "Register a new seller",
            description = "Registers a new seller by accepting their signup details and returns the registered seller" +
                    " information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Seller registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            SellerResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<SellerResponseDto> sellerSignup(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Seller signup details",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation =
                            SellerSignupDto.class))
            )
            @RequestBody SellerSignupDto sellerSignupDto
    ) {
        SellerResponseDto sellerResponseDto = signupService.sellerSignup(sellerSignupDto);
        return ResponseEntity.ok(sellerResponseDto);
    }

    /**
     * Endpoint for Buyer Signup.
     * This API allows a new buyer to register with their details. The buyer's data is
     * then processed by the signup service to create a new buyer account.
     *
     * @param buyerSignupDto The buyer's signup data including name, email, password, and optional addresses.
     * @return ResponseEntity containing the BuyerResponseDto, which holds the newly created buyer's information.
     */
    @Operation(
            summary = "Buyer Signup",
            description = "Allows a new buyer to register by providing their details, including name, email, " +
                    "password, and optional addresses."
    )
    @PostMapping("/buyer")
    public ResponseEntity<BuyerResponseDto> buyerSignup(
            @RequestBody @Schema(description = "Buyer signup data including name, email, password, and " +
                    "addresses.")
            BuyerSignupDto buyerSignupDto
    ) {
        // Calling the signup service to register the new buyer and get the response
        BuyerResponseDto buyerResponseDto = signupService.buyerSignup(buyerSignupDto);

        // Returning the response with the newly created buyer's information
        return ResponseEntity.ok(buyerResponseDto);
    }

}
