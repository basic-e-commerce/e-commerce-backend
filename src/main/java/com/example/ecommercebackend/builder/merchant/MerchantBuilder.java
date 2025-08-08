package com.example.ecommercebackend.builder.merchant;

import com.example.ecommercebackend.config.EncryptionUtils;
import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.merchant.MerchantPublicDetailResponse;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantCreateDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantResponseDto;
import com.example.ecommercebackend.dto.user.address.AddressDetailDto;
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
                EncryptionUtils.decrypt(merchantCreateDto.getPhoneNo()),
                merchantCreateDto.getEmail(),
                merchantCreateDto.getMinOrderAmount(),
                merchantCreateDto.getShippingFee(),
                EncryptionUtils.decrypt(merchantCreateDto.getEmailPassword()),
                merchantCreateDto.getInstagram(),
                merchantCreateDto.getInstagramLink(),
                merchantCreateDto.getFooterDescription(),
                merchantCreateDto.getOpenCloseHours()
        );
    }

    public MerchantResponseDto merchantToMerchantResponseDto(Merchant merchant) {
        return new MerchantResponseDto(
                merchant.getId(),
                merchant.getName(),
                merchant.getAddressLink(),
                merchant.getPhoneNo(),
                merchant.getEmail(),
                merchant.getMinOrderAmount(),
                merchant.getShippingFee(),
                EncryptionUtils.decrypt(merchant.getEmailPassword()),
                merchant.getInstagram(),
                merchant.getInstagramLink(),
                merchant.getFooterDescription(),
                merchant.getOpenCloseHours(),
                merchant.getCoverImage() != null ? new ImageDetailDto(
                        merchant.getCoverImage().getId(),
                        merchant.getCoverImage().getName(),
                        merchant.getCoverImage().getResolution(),
                        merchant.getCoverImage().getName(),
                        merchant.getCoverImage().getUrl(),
                        0
                ) : null,

                merchant.getAddress().getId(),
                merchant.getAddress().getTitle(),
                merchant.getAddress().getFirstName(),
                merchant.getAddress().getLastName(),
                merchant.getAddress().getUsername(),
                merchant.getAddress().getCountry().getUpperName(),
                merchant.getAddress().getCountry().getIso(),
                merchant.getAddress().getCity().getName(),
                merchant.getAddress().getCity().getCityCode(),
                merchant.getAddress().getDistrict().getName(),
                merchant.getAddress().getDistrict().getDistrictId(),
                merchant.getAddress().getPostalCode(),
                merchant.getAddress().getPhoneNo(),
                merchant.getAddress().getAddressLine1(),
                merchant.getAddress().getGeliverId() != null ? merchant.getAddress().getGeliverId() : null
        );

    }

    public MerchantPublicDetailResponse merchantToMerchantPublicDetailResponse(Merchant merchant) {

        return new MerchantPublicDetailResponse(
                merchant.getId(),
                merchant.getName(),
                merchant.getAddressLink(),
                merchant.getPhoneNo(),
                merchant.getEmail(),
                merchant.getMinOrderAmount(),
                merchant.getShippingFee(),
                merchant.getInstagram(),
                merchant.getInstagramLink(),
                merchant.getFooterDescription(),
                merchant.getOpenCloseHours(),
                merchant.getCoverImage() != null ? new ImageDetailDto(
                        merchant.getCoverImage().getId(),
                        merchant.getCoverImage().getName(),
                        merchant.getCoverImage().getResolution(),
                        merchant.getCoverImage().getName(),
                        merchant.getCoverImage().getUrl(),
                        0
                ) : null,

                merchant.getAddress().getId(),
                merchant.getAddress().getTitle(),
                merchant.getAddress().getFirstName(),
                merchant.getAddress().getLastName(),
                merchant.getAddress().getUsername(),
                merchant.getAddress().getCountry().getUpperName(),
                merchant.getAddress().getCountry().getIso(),
                merchant.getAddress().getCity().getName(),
                merchant.getAddress().getCity().getCityCode(),
                merchant.getAddress().getDistrict().getName(),
                merchant.getAddress().getDistrict().getDistrictId(),
                merchant.getAddress().getPostalCode(),
                merchant.getAddress().getPhoneNo(),
                merchant.getAddress().getAddressLine1(),
                merchant.getAddress().getGeliverId() != null ? merchant.getAddress().getGeliverId() : null

        );

    }
}
