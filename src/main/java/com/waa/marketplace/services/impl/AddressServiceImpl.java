package com.waa.marketplace.services.impl;

import com.waa.marketplace.dtos.requests.AddressRequestDto;
import com.waa.marketplace.dtos.responses.AddressResponseDto;
import com.waa.marketplace.entites.Address;
import com.waa.marketplace.entites.Buyer;
import com.waa.marketplace.repositories.AddressRepository;
import com.waa.marketplace.repositories.BuyerRepository;
import com.waa.marketplace.services.AddressService;
import com.waa.marketplace.utils.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final BuyerRepository buyerRepository;

    public AddressServiceImpl(AddressRepository addressRepository, BuyerRepository buyerRepository) {
        this.addressRepository = addressRepository;
        this.buyerRepository = buyerRepository;
    }

    @Override
    public AddressResponseDto createAddress(AddressRequestDto addressRequestDto) {
        Buyer buyer = getLoggedInBuyer();

        Address address = Address.builder()
                .street(addressRequestDto.getStreet())
                .city(addressRequestDto.getCity())
                .state(addressRequestDto.getState())
                .zipCode(addressRequestDto.getZipCode())
                .country(addressRequestDto.getCountry())
                .buyer(buyer)
                .build();

        Address savedAddress = addressRepository.save(address);
        return mapToDto(savedAddress);
    }

    @Override
    public List<AddressResponseDto> getAddresses() {
        Buyer buyer = getLoggedInBuyer();
        List<Address> addresses = addressRepository.findByBuyerId(buyer.getId());
        return addresses.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public AddressResponseDto updateAddress(Long id, AddressRequestDto addressRequestDto) {
        Buyer buyer = getLoggedInBuyer();
        Address address = addressRepository.findByIdAndBuyerId(id, buyer.getId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        address.setStreet(addressRequestDto.getStreet());
        address.setCity(addressRequestDto.getCity());
        address.setState(addressRequestDto.getState());
        address.setZipCode(addressRequestDto.getZipCode());
        address.setCountry(addressRequestDto.getCountry());

        Address updatedAddress = addressRepository.save(address);
        return mapToDto(updatedAddress);
    }

    @Override
    public void deleteAddress(Long id) {
        Buyer buyer = getLoggedInBuyer();
        Address address = addressRepository.findByIdAndBuyerId(id, buyer.getId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
        addressRepository.delete(address);
    }

    @Override
    public AddressResponseDto getAddressById(Long id) {
        Buyer buyer = getLoggedInBuyer();
        Address address = addressRepository.findByIdAndBuyerId(id, buyer.getId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
        return mapToDto(address);
    }

    private AddressResponseDto mapToDto(Address address) {
        return AddressResponseDto.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .country(address.getCountry())
                .build();
    }

    private Buyer getLoggedInBuyer() {
        Long id = SecurityUtils.getId();
        if (id == null) {
            throw new IllegalStateException("No Buyer ID found for the " +
                    "logged-in user.");
        }
        return buyerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Buyer not found"));
    }
}
