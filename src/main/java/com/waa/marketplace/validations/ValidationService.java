package com.waa.marketplace.validations;

import com.waa.marketplace.enums.AddressType;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    public void validateAddressType(String type) {
        if (!type.equals(AddressType.BILLING.name()) && !type.equals(AddressType.SHIPPING.name())) {
            throw new IllegalArgumentException("Invalid address type");
        }
    }
}
