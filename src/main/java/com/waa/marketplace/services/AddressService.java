package com.waa.marketplace.services;

import com.waa.marketplace.dtos.requests.AddressRequestDto;
import com.waa.marketplace.dtos.responses.AddressResponseDto;

import java.util.List;

public interface AddressService {
    AddressResponseDto createAddress(AddressRequestDto addressRequestDto);

    List<AddressResponseDto> getAddresses();

    AddressResponseDto updateAddress(Long id, AddressRequestDto addressRequestDto);

    void deleteAddress(Long id);

    AddressResponseDto getAddressById(Long id);
}
