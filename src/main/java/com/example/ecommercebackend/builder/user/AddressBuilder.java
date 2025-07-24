package com.example.ecommercebackend.builder.user;

import com.example.ecommercebackend.dto.merchant.merchant.MerchantCreateDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantUpdateDto;
import com.example.ecommercebackend.dto.product.shipping.AddressApiDto;
import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Address;
import com.example.ecommercebackend.entity.user.City;
import com.example.ecommercebackend.entity.user.District;
import org.springframework.stereotype.Component;

@Component
public class AddressBuilder {
    public Address addressCreateDtoToAddress(AddressCreateDto addressCreateDto, Country country, City city,District district,Boolean isReceiptAddress) {
        return new Address(
                addressCreateDto.getTitle(),
                country,
                addressCreateDto.getFirstName(),
                addressCreateDto.getLastName(),
                addressCreateDto.getUsername(),
                city,
                district,
                addressCreateDto.getAddressLine1(),
                addressCreateDto.getPostalCode(),
                addressCreateDto.getPhoneNo(),
                isReceiptAddress
        );
    }

    public Address addressApiDtoToAddress(AddressApiDto addressApiDto, Country country, MerchantCreateDto merchantCreateDto,City city,District district,Boolean isReceiptAddress) {
        return new Address(
                addressApiDto.getName(),
                country,
                merchantCreateDto.getFirstName(),
                merchantCreateDto.getLastName(),
                merchantCreateDto.getEmail(),
                city,
                district,
                merchantCreateDto.getAddressLine1(),
                merchantCreateDto.getPostalCode(),
                merchantCreateDto.getPhoneNo(),
                isReceiptAddress
        );
    }

    public Address addressApiDtoToAddress(AddressApiDto addressApiDto, Country country, MerchantUpdateDto merchantCreateDto, City city, District district,Boolean isReceiptAddress) {
        return new Address(
                addressApiDto.getName(),
                country,
                merchantCreateDto.getFirstName(),
                merchantCreateDto.getLastName(),
                merchantCreateDto.getEmail(),
                city,
                district,
                merchantCreateDto.getAddressLine1(),
                merchantCreateDto.getPostalCode(),
                merchantCreateDto.getPhoneNo(),
                isReceiptAddress
        );
    }
}
