package com.waa.marketplace.controllers;

import com.waa.marketplace.dtos.requests.AddressRequestDto;
import com.waa.marketplace.dtos.responses.AddressResponseDto;
import com.waa.marketplace.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @Operation(summary = "Create a new address")
    @PostMapping
    public ResponseEntity<AddressResponseDto> createAddress(
            @Valid @RequestBody AddressRequestDto addressRequestDto) {
        AddressResponseDto createdAddress = addressService.createAddress(addressRequestDto);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all addresses for the logged-in user")
    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAddresses(@RequestParam(required = false) String type) {
        List<AddressResponseDto> addresses = addressService.getAddresses(type);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @Operation(summary = "Get all addresses for the logged-in user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable Long id) {
        AddressResponseDto addresses = addressService.getAddressById(id);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @Operation(summary = "Update an address")
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDto> updateAddress(
            @PathVariable Long id, @Valid @RequestBody AddressRequestDto addressRequestDto) {
        AddressResponseDto updatedAddress = addressService.updateAddress(id, addressRequestDto);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @Operation(summary = "Delete an address")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
