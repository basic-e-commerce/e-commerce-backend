package com.example.ecommercebackend.builder.merchant;

import com.example.ecommercebackend.dto.merchant.merchant.MerchantCreateDto;
import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.user.Address;
import org.springframework.stereotype.Component;

@Component
public class MerchantBuilder {

    public Merchant merchantCreateDtoToMerchant(MerchantCreateDto merchantCreateDto, Address address) {
        return new Merchant(
                merchantCreateDto.getName(),
                address,
                null,
                merchantCreateDto.getPhoneNo(),
                merchantCreateDto.getEmail(),
                merchantCreateDto.getMinOrderAmount()
        );
    }

}
