package com.example.ecommercebackend.builder.user;

import com.example.ecommercebackend.dto.merchant.merchant.MerchantCreateDto;
import com.example.ecommercebackend.dto.product.shipping.AddressApiDto;
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
                addressCreateDto.getFirstName(),
                addressCreateDto.getLastName(),
                addressCreateDto.getCity(),
                addressCreateDto.getAddressLine1(),
                addressCreateDto.getPostalCode(),
                addressCreateDto.getPhoneNo()
        );
    }

    public Address addressApiDtoToAddress(AddressApiDto addressApiDto, Country country, MerchantCreateDto merchantCreateDto) {
        Address address = new Address(
                addressApiDto.getName(),
                country,
                merchantCreateDto.getFirstName(),
                merchantCreateDto.getLastName(),
                merchantCreateDto.getCity(),
                merchantCreateDto.getAddressLine1(),
                merchantCreateDto.getPostalCode(),
                merchantCreateDto.getPhoneNo()
        );
        address.setCityId(addressApiDto.getCityCode());
        address.setCity(addressApiDto.getCityName());
        address.setDistrictID(addressApiDto.getDistrictID());
        return address;
    }
}
