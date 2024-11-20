package com.waa.marketplace.controllers;

import com.waa.marketplace.dtos.requests.AddressRequestDto;
import com.waa.marketplace.dtos.responses.AddressResponseDto;
import com.waa.marketplace.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to manage CRUD operations for addresses associated with the buyer.
 */
@RestController
@RequestMapping("/api/v1/buyer/addresses")
public class AddressController {

    private final AddressService addressService;

    /**
     * Constructor to inject the AddressService dependency.
     *
     * @param addressService the service layer for address-related operations
     */
    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * Create a new address for the buyer.
     *
     * @param addressRequestDto the address details to create
     * @return ResponseEntity containing the created AddressResponseDto
     */
    @Operation(summary = "Create a new address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<AddressResponseDto> createAddress(
            @Valid @RequestBody AddressRequestDto addressRequestDto) {
        AddressResponseDto createdAddress = addressService.createAddress(addressRequestDto);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    /**
     * Retrieve all addresses associated with the logged-in user.
     *
     * @return ResponseEntity containing a list of AddressResponseDto
     */
    @Operation(summary = "Get all addresses for the logged-in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved addresses")
    })
    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAddresses() {
        List<AddressResponseDto> addresses = addressService.getAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    /**
     * Retrieve a specific address by its ID.
     *
     * @param id the ID of the address to retrieve
     * @return ResponseEntity containing the AddressResponseDto
     */
    @Operation(summary = "Get an address by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the address"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable Long id) {
        AddressResponseDto address = addressService.getAddressById(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    /**
     * Update an existing address.
     *
     * @param id                the ID of the address to update
     * @param addressRequestDto the new address details
     * @return ResponseEntity containing the updated AddressResponseDto
     */
    @Operation(summary = "Update an address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address updated successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable Long id, @Valid @RequestBody AddressRequestDto addressRequestDto) {
        AddressResponseDto updatedAddress = addressService.updateAddress(id, addressRequestDto);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    /**
     * Delete an address by its ID.
     *
     * @param id the ID of the address to delete
     * @return ResponseEntity with no content
     */
    @Operation(summary = "Delete an address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Address deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
