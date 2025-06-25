package com.example.ecommercebackend.builder.merchant;

import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantCreateDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantResponseDto;
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
                merchantCreateDto.getMinOrderAmount(),
                merchantCreateDto.getShippingFee(),
                merchantCreateDto.getEmailPassword()
        );
    }

    public MerchantResponseDto merchantToMerchantResponseDto(Merchant merchant) {
        return new MerchantResponseDto(
                merchant.getId(),
                merchant.getName(),
                merchant.getAddress().getAddressLine1()+" "+merchant.getAddress().getCity()+"/"+merchant.getAddress().getCountry().getName(),
                new ImageDetailDto(
                        merchant.getCoverImage().getId(),
                        merchant.getCoverImage().getName(),
                        merchant.getCoverImage().getResolution(),
                        merchant.getCoverImage().getName(),
                        merchant.getCoverImage().getUrl(),
                        0
                        ),
                merchant.getPhoneNo(),
                merchant.getEmail(),
                merchant.getMinOrderAmount(),
                merchant.getShippingFee()
        );
    }

}
