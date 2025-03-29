package com.example.ecommercebackend.builder.user;

import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressBuilder {
    public Address addressCreateDtoToAddress(AddressCreateDto addressCreateDto, Country country) {
        return new Address(
                addressCreateDto.getTitle(),
                country,
                addressCreateDto.getCity(),
                addressCreateDto.getAddressLine1(),
                addressCreateDto.getAddressLine2(),
                addressCreateDto.getPostalCode(),
                addressCreateDto.getPhoneNo()
        );
    }
}
