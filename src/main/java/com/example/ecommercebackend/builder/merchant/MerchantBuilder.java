package com.example.ecommercebackend.builder.merchant;

import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantCreateDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantResponseDto;
import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.user.Address;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MerchantBuilder {

    public Merchant merchantCreateDtoToMerchant(MerchantCreateDto merchantCreateDto, Address address, List<Address> sendingAddresses) {
        return new Merchant(
                merchantCreateDto.getName(),
                address,
                merchantCreateDto.getAddressLink(),
                sendingAddresses,
                null,
                merchantCreateDto.getPhoneNo(),
                merchantCreateDto.getPhoneNoLink(),
                merchantCreateDto.getEmail(),
                merchantCreateDto.getEmailLink(),
                merchantCreateDto.getMinOrderAmount(),
                merchantCreateDto.getShippingFee(),
                merchantCreateDto.getEmailPassword(),
                merchantCreateDto.getInstagram(),
                merchantCreateDto.getInstagramLink(),
                merchantCreateDto.getWpLink(),
                merchantCreateDto.getFooterDescription(),
                merchantCreateDto.getOpenCloseHours()
        );
    }

    public MerchantResponseDto merchantToMerchantResponseDto(Merchant merchant) {
        return new MerchantResponseDto(
                merchant.getId(),
                merchant.getName(),
                merchant.getAddress() != null ? merchant.getAddress().getCountry().getName() : null,
                merchant.getAddress().getCity() != null ? merchant.getAddress().getCity().getName() : null,
                merchant.getAddress().getDistrict() != null ? merchant.getAddress().getDistrict().getName() : null,
                merchant.getAddress() != null ? merchant.getAddress().getAddressLine1() : null,
                merchant.getAddress() != null ? merchant.getAddress().getPostalCode() : null,
                merchant.getAddressLink(),
                merchant.getPhoneNo(),
                merchant.getPhoneNoLink(),
                merchant.getEmail(),
                merchant.getEmailLink(),
                merchant.getMinOrderAmount(),
                merchant.getShippingFee(),
                merchant.getEmailPassword(),
                merchant.getInstagram(),
                merchant.getInstagramLink(),
                merchant.getWpLink(),
                merchant.getFooterDescription(),
                merchant.getOpenCloseHours(),
                merchant.getCoverImage() != null ? new ImageDetailDto(
                        merchant.getCoverImage().getId(),
                        merchant.getCoverImage().getName(),
                        merchant.getCoverImage().getResolution(),
                        merchant.getCoverImage().getName(),
                        merchant.getCoverImage().getUrl(),
                        0
                ) : null
        );

    }

}
