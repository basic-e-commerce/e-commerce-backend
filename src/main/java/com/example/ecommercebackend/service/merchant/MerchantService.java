package com.example.ecommercebackend.service.merchant;

import com.example.ecommercebackend.builder.merchant.MerchantBuilder;
import com.example.ecommercebackend.dto.file.CoverImageRequestDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantCreateDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantResponseDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantUpdateDto;
import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.user.Address;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.merchant.MerchantRepository;
import com.example.ecommercebackend.service.file.MerchantImageService;
import com.example.ecommercebackend.service.user.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchantService {
    private final MerchantRepository merchantRepository;
    private final MerchantBuilder merchantBuilder;
    private final AddressService addressService;
    private final MerchantImageService merchantImageService;

    public MerchantService(MerchantRepository merchantRepository, MerchantBuilder merchantBuilder, AddressService addressService, MerchantImageService merchantImageService) {
        this.merchantRepository = merchantRepository;
        this.addressService = addressService;
        this.merchantBuilder = merchantBuilder;
        this.merchantImageService = merchantImageService;
    }
    public MerchantResponseDto createMerchant(MerchantCreateDto merchantCreateDto) {
        AddressCreateDto addressCreateDto = new AddressCreateDto(merchantCreateDto.getTitle(),merchantCreateDto.getFirstName(),merchantCreateDto.getLastName(),merchantCreateDto.getCountryName(), merchantCreateDto.getCity(), merchantCreateDto.getAddressLine1(), merchantCreateDto.getPostalCode(), merchantCreateDto.getPhoneNo());
        Address address = addressService.createAddress(addressCreateDto);

        Merchant merchant = merchantBuilder.merchantCreateDtoToMerchant(merchantCreateDto, address);
        Merchant save = merchantRepository.save(merchant);

        CoverImageRequestDto coverImageRequestDto = new CoverImageRequestDto(merchantCreateDto.getImage());
        CoverImage coverImage = merchantImageService.save(coverImageRequestDto, save.getId());
        merchant.setCoverImage(coverImage);
        return merchantBuilder.merchantToMerchantResponseDto(merchantRepository.save(merchant));
    }

    public MerchantResponseDto updateMerchant(MerchantUpdateDto merchantCreateDto) {
        Merchant merchant= getMerchant();
        AddressCreateDto addressCreateDto = new AddressCreateDto(merchantCreateDto.getTitle(),merchantCreateDto.getFirstName(),merchantCreateDto.getLastName(), merchantCreateDto.getCountryName(), merchantCreateDto.getCity(), merchantCreateDto.getAddressLine1(), merchantCreateDto.getPostalCode(), merchantCreateDto.getPhoneNo());
        Address address = addressService.updateAddressById(merchant.getAddress().getId(),addressCreateDto);
        merchant.setAddress(address);
        merchant.setName(merchantCreateDto.getName());
        merchant.setPhoneNo(merchantCreateDto.getPhoneNo());
        merchant.setPhoneNoLink(merchantCreateDto.getPhoneNoLink());
        merchant.setEmail(merchantCreateDto.getEmail());
        merchant.setEmailLink(merchantCreateDto.getEmailLink());
        merchant.setEmailPassword(merchantCreateDto.getEmailPassword());
        merchant.setMinOrderAmount(merchantCreateDto.getMinOrderAmount());
        merchant.setShippingFee(merchantCreateDto.getShippingFee());
        merchant.setInstagram(merchantCreateDto.getInstagram());
        merchant.setInstagramLink(merchantCreateDto.getInstagramLink());
        merchant.setWpLink(merchantCreateDto.getWpLink());
        merchant.setFooterDescription(merchantCreateDto.getFooterDescription());
        merchant.setOpenCloseHours(merchantCreateDto.getOpenCloseHours());
        return merchantBuilder.merchantToMerchantResponseDto(merchantRepository.save(merchant));
    }

    public Merchant getMerchant() {
        return merchantRepository.findAll().stream().findFirst().orElseThrow(()-> new NotFoundException("Merchant "+ ExceptionMessage.NOT_FOUND.getMessage()));
    }
    public List<Merchant> getMerchants() {
        return merchantRepository.findAll();
    }
}
