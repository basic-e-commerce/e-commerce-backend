package com.example.ecommercebackend.service.merchant;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.builder.merchant.MerchantBuilder;
import com.example.ecommercebackend.dto.file.CoverImageRequestDto;
import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.merchant.MerchantPublicDetailResponse;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantCreateDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantResponseDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantUpdateDto;
import com.example.ecommercebackend.dto.product.shipping.AddressApiDto;
import com.example.ecommercebackend.dto.product.shipping.AddressReceiptDto;
import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import com.example.ecommercebackend.dto.user.address.AddressDetailDto;
import com.example.ecommercebackend.entity.file.CoverImage;
import com.example.ecommercebackend.entity.merchant.CustomCargoContract;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.user.Address;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.merchant.MerchantRepository;
import com.example.ecommercebackend.service.file.MerchantImageService;
import com.example.ecommercebackend.service.product.shipping.ShippingAddressService;
import com.example.ecommercebackend.service.user.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MerchantService {
    private final MerchantRepository merchantRepository;
    private final MerchantBuilder merchantBuilder;
    private final AddressService addressService;
    private final MerchantImageService merchantImageService;
    private final ShippingAddressService shippingAddressService;

    public MerchantService(MerchantRepository merchantRepository, MerchantBuilder merchantBuilder, AddressService addressService, MerchantImageService merchantImageService, ShippingAddressService shippingAddressService) {
        this.merchantRepository = merchantRepository;
        this.addressService = addressService;
        this.merchantBuilder = merchantBuilder;
        this.merchantImageService = merchantImageService;
        this.shippingAddressService = shippingAddressService;
    }

    public MerchantResponseDto updateMerchant(MerchantUpdateDto merchantCreateDto) {
        Merchant merchant= getMerchant();
        AddressCreateDto addressCreateDto = new AddressCreateDto(
                merchantCreateDto.getTitle(),
                merchantCreateDto.getFirstName(),
                merchantCreateDto.getLastName(),
                merchantCreateDto.getEmail(),
                merchantCreateDto.getCountryName(),
                merchantCreateDto.getCityCode(),
                merchantCreateDto.getDistrictId(),
                merchantCreateDto.getAddressLine1(),
                merchantCreateDto.getPostalCode(),
                merchantCreateDto.getPhoneNo());
        addressService.updateAddressById(merchant.getAddress().getId(),addressCreateDto);

        merchant.setName(merchantCreateDto.getName());
        merchant.setPhoneNo(merchantCreateDto.getPhoneNo());
        merchant.setEmail(merchantCreateDto.getEmail());
        merchant.setEmailPassword(merchantCreateDto.getEmailPassword());
        merchant.setMinOrderAmount(merchantCreateDto.getMinOrderAmount());
        merchant.setShippingFee(merchantCreateDto.getShippingFee());
        merchant.setInstagram(merchantCreateDto.getInstagram());
        merchant.setInstagramLink(merchantCreateDto.getInstagramLink());
        merchant.setFooterDescription(merchantCreateDto.getFooterDescription());
        merchant.setOpenCloseHours(merchantCreateDto.getOpenCloseHours());

        return merchantBuilder.merchantToMerchantResponseDto(merchantRepository.save(merchant));
    }

    public Merchant getMerchant() {
        return merchantRepository.findAll().stream().findFirst().orElseThrow(() -> new NotFoundException("Merchant " + ExceptionMessage.NOT_FOUND.getMessage()));
    }
    public List<MerchantResponseDto> getMerchants() {
        return merchantRepository.findAll().stream().map(merchantBuilder::merchantToMerchantResponseDto).collect(Collectors.toList());
    }

    public ImageDetailDto updateCoverImage(MultipartFile file) {
        Merchant merchant = getMerchant();
        if (merchant.getCoverImage() != null) {
            merchantImageService.delete(merchant.getCoverImage().getId());
            merchant.setCoverImage(null);
            merchantRepository.save(merchant);
        }
        CoverImageRequestDto coverImageRequestDto = new CoverImageRequestDto(file);
        CoverImage coverImage = merchantImageService.save(coverImageRequestDto, merchant.getId());
        merchant.setCoverImage(coverImage);
        merchantRepository.save(merchant);
        return new ImageDetailDto(
                coverImage.getId(),
                coverImage.getName(),
                coverImage.getResolution(),
                coverImage.getName(),
                coverImage.getUrl(),
                0
        );
    }

    public AddressDetailDto createSendingAddress(@NotNullParam AddressCreateDto addressCreateDto) {
        Address address = addressService.createAddress(addressCreateDto,false);
        Merchant merchant = getMerchant();
        merchant.getSendingAddresses().add(address);
        merchantRepository.save(merchant);
        AddressApiDto addressApiDto = new AddressApiDto(
                address.getFirstName()+ " "+ address.getLastName(),
                merchant.getEmail(),
                address.getPhoneNo(),
                address.getAddressLine1(),
                "",
                address.getCountry().getIso(),
                address.getCity().getName(),
                address.getCity().getCityCode(),
                address.getDistrict().getName(),
                address.getDistrict().getDistrictId(),
                address.getPostalCode(),
                false,
                UUID.randomUUID().toString()
        );
        AddressReceiptDto sendingAddress = shippingAddressService.createSendingAddress(addressApiDto);
        address.setGeliverId(sendingAddress.getId());
        Address save = addressService.save(address);

        return new AddressDetailDto(
                save.getId(),
                save.getTitle(),
                save.getFirstName(),
                save.getLastName(),
                save.getUsername(),
                save.getCountry().getUpperName(),
                save.getCountry().getIso(),
                save.getCity().getName(),
                save.getCity().getCityCode(),
                save.getDistrict().getName(),
                save.getDistrict().getDistrictId(),
                save.getPostalCode(),
                save.getPhoneNo(),
                save.getAddressLine1(),
                save.getGeliverId()
        );
    }

    public String removeSendingAddress(@NotNullParam Integer addressId) {
        Address address = addressService.findAddressById(addressId);
        Merchant merchant = getMerchant();

        if (!merchant.getSendingAddresses().contains(address))
            throw new NotFoundException("Gönderici adresi bulunamadı!");

        shippingAddressService.deleteShippingAddress(address.getGeliverId());

        merchant.getSendingAddresses().remove(address);
        merchantRepository.save(getMerchant());
        return "Gönderici adresi kaldırıldı";
    }

    public AddressDetailDto selectDefaultSendingAddress(Integer addressId) {
        Merchant merchant = getMerchant();
        List<Address> sendingAddresses = merchant.getSendingAddresses();
        Address defaultaddress = null;
        boolean flag = false;
        for (Address address : sendingAddresses) {
            if (addressId.equals(address.getId())){
                flag = true;
                merchant.setDefaultSendingAddress(address);
                defaultaddress = address;
            }
        }
        if (!flag)
            throw new NotFoundException("adres bulunamadı!");

        return new AddressDetailDto(
                defaultaddress.getId(),
                defaultaddress.getTitle(),
                defaultaddress.getFirstName(),
                defaultaddress.getLastName(),
                defaultaddress.getUsername(),
                defaultaddress.getCountry().getUpperName(),
                defaultaddress.getCountry().getIso(),
                defaultaddress.getCity().getName(),
                defaultaddress.getCity().getCityCode(),
                defaultaddress.getDistrict().getName(),
                defaultaddress.getDistrict().getDistrictId(),
                defaultaddress.getPostalCode(),
                defaultaddress.getPhoneNo(),
                defaultaddress.getAddressLine1(),
                defaultaddress.getGeliverId()
        );
    }

    public List<AddressDetailDto> getSendingAddresses() {
        Merchant merchant = getMerchant();
        return merchant.getSendingAddresses().stream().map(x->{
            return new AddressDetailDto(
                    x.getId(),
                    x.getTitle(),
                    x.getFirstName(),
                    x.getLastName(),
                    x.getUsername(),
                    x.getCountry().getUpperName(),
                    x.getCountry().getIso(),
                    x.getCity().getName(),
                    x.getCity().getCityCode(),
                    x.getDistrict().getName(),
                    x.getDistrict().getDistrictId(),
                    x.getPostalCode(),
                    x.getPhoneNo(),
                    x.getAddressLine1(),
                    x.getGeliverId()
            );
        }).toList();
    }

    public void save(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    public String selectDefaultCustomCargoContact(Integer customContractId) {
        Merchant merchant = getMerchant();
        CustomCargoContract customCargoContract = merchant.getCustomCargoContracts().stream().filter(x->x.getId() == customContractId).findFirst().orElse(null);

        if (customCargoContract == null)
            throw new NotFoundException("Kargo anlaşması bulunamadı");

        merchant.setDefaultCustomCargoContract(customCargoContract);
        return "Özel Kargo anlaşması default olarak güncellendi!";
    }

    public MerchantPublicDetailResponse getDetail() {
        return merchantBuilder.merchantToMerchantPublicDetailResponse(merchantRepository.findAll().stream().findFirst().get());
    }
}
