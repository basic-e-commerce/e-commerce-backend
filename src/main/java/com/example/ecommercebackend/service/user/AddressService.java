package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.builder.user.AddressBuilder;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantCreateDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantUpdateDto;
import com.example.ecommercebackend.dto.product.shipping.AddressApiDto;
import com.example.ecommercebackend.dto.product.shipping.AddressReceiptDto;
import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Address;
import com.example.ecommercebackend.entity.user.City;
import com.example.ecommercebackend.entity.user.District;
import com.example.ecommercebackend.exception.BadRequestException;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.user.AddressRepository;
import com.example.ecommercebackend.service.product.shipping.CountryService;
import com.example.ecommercebackend.service.product.shipping.ShippingAddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class AddressService {
    private static final Logger log = LoggerFactory.getLogger(AddressService.class);
    private final CountryService countryService;
    private final AddressRepository addressRepository;
    private final AddressBuilder addressBuilder;
    private final CityService cityService;
    private final DistrictService districtService;
    private final ShippingAddressService shippingAddressService;

    public AddressService(CountryService countryService, AddressRepository addressRepository, AddressBuilder addressBuilder, CityService cityService, DistrictService districtService, ShippingAddressService shippingAddressService) {
        this.countryService = countryService;
        this.addressRepository = addressRepository;
        this.addressBuilder = addressBuilder;
        this.cityService = cityService;
        this.districtService = districtService;
        this.shippingAddressService = shippingAddressService;
    }

    public Address createAddress(AddressCreateDto addressCreateDto,Boolean isReceiptAddress) {
        Country country = countryService.findCountryByUpperName(addressCreateDto.getCountryName());
        System.out.println(1);
        City city = cityService.findByCityCode(addressCreateDto.getCityCode());
        System.out.println(2);
        District district = districtService.findByDistrictId(addressCreateDto.getDistrictId());
        System.out.println(3);
        Address address = addressBuilder.addressCreateDtoToAddress(addressCreateDto,country,city,district,isReceiptAddress);
        System.out.println(4);
        Address save = addressRepository.save(address);

        System.out.println(5);
        if (isReceiptAddress){
            System.out.println(6);
            System.out.println(addressCreateDto.getPhoneNo() + "     " +addressCreateDto.getAddressLine1());

            Random random = new Random();
            AddressApiDto addressApiDto = new AddressApiDto(
                    addressCreateDto.getFirstName()+ " " + addressCreateDto.getLastName(),
                    addressCreateDto.getUsername(),
                    addressCreateDto.getPhoneNo(),
                    addressCreateDto.getAddressLine1(),
                    "",
                    country.getIso(),
                    city.getName(),
                    city.getCityCode(),
                    district.getName(),
                    district.getDistrictId(),
                    addressCreateDto.getPostalCode(),
                    true,
                    addressCreateDto.getFirstName()+ " " + addressCreateDto.getLastName()+"-"+(100000+random.nextInt(90000))
            );
            try {
                System.out.println(1);
                addressApiDto.setShortName(address.getShortName());
                AddressReceiptDto receivingAddress = shippingAddressService.createReceivingAddress(addressApiDto);
                save.setGeliverId(receivingAddress.getId());
            } catch (JsonProcessingException e) {
                log.error("Address creation 3. parti yazılıma kaydedilemedi", e);
            }
        }else {
            System.out.println(1);
            Random random = new Random();
            AddressApiDto addressApiDto = new AddressApiDto(
                    addressCreateDto.getFirstName()+ " " + addressCreateDto.getLastName(),
                    addressCreateDto.getUsername(),
                    addressCreateDto.getPhoneNo(),
                    addressCreateDto.getAddressLine1(),
                    "",
                    country.getIso(),
                    city.getName(),
                    city.getCityCode(),
                    district.getName(),
                    district.getDistrictId(),
                    addressCreateDto.getPostalCode(),
                    true,
                    addressCreateDto.getFirstName()+ " " + addressCreateDto.getLastName()+"-"+(100000+random.nextInt(90000))
            );

            addressApiDto.setShortName(address.getShortName());
            AddressReceiptDto sendingAddress = shippingAddressService.createSendingAddress(addressApiDto);
            save.setGeliverId(sendingAddress.getId());
        }

        return addressRepository.save(save);
    }

    public Address createAddress(AddressApiDto addressApiDto, MerchantCreateDto merchantCreateDto,Boolean isReceiptAddress) {
        Country country = countryService.findCountryByUpperName("TURKIYE");
        City city = cityService.findByCityCode(addressApiDto.getCityCode());
        District district = districtService.findByDistrictId(addressApiDto.getDistrictID());
        Address address = addressBuilder.addressApiDtoToAddress(addressApiDto,country,merchantCreateDto,city,district,isReceiptAddress);
        Address save = addressRepository.save(address);
        if (isReceiptAddress){
            addressApiDto.setShortName(address.getShortName());
            try {
                AddressReceiptDto receivingAddress = shippingAddressService.createReceivingAddress(addressApiDto);
                save.setGeliverId(receivingAddress.getId());
            } catch (JsonProcessingException e) {
                log.error("3. parti servis için adres create methodunda json işleme hatası veriyor!");
                throw new BadRequestException("3. parti servis hatası");
            }
        }else {
            addressApiDto.setShortName(address.getShortName());
            AddressReceiptDto sendingAddress = shippingAddressService.createSendingAddress(addressApiDto);
            save.setGeliverId(sendingAddress.getId());
        }
        return save;
    }

    public Address createAddress(AddressApiDto addressApiDto, MerchantUpdateDto merchantCreateDto,Boolean isReceiptAddress) {
        Country country = countryService.findCountryByUpperName("TURKIYE");
        City city = cityService.findByCityCode(addressApiDto.getCityCode());
        District district = districtService.findByDistrictId(addressApiDto.getDistrictID());
        Address address = addressBuilder.addressApiDtoToAddress(addressApiDto,country,merchantCreateDto,city,district,isReceiptAddress);
        return addressRepository.save(address);
    }

    public Address findAddressById(Integer id) {
        return addressRepository.findById(id).orElseThrow(()-> new NotFoundException("Address "+ ExceptionMessage.NOT_FOUND.getMessage()));
    }

    public String deleteAddressById(Integer id) {
        Address address = findAddressById(id); // veya direkt repository.getById(id)
        addressRepository.delete(address);
        return "address deleted";
    }

    public Address updateAddressById(Integer id, AddressCreateDto addressCreateDto) {
        Address address = findAddressById(id);
        System.out.println(address.getPhoneNo());
        System.out.println(address.getAddressLine1());
        Country country = countryService.findCountryByUpperName(addressCreateDto.getCountryName());

        boolean isUpdated = false;

        if (!Objects.equals(address.getTitle(), addressCreateDto.getTitle())) {
            address.setTitle(addressCreateDto.getTitle());
            isUpdated = true;
        }
        if (!Objects.equals(address.getCountry().getIso3(), addressCreateDto.getCountryName())) {
            address.setCountry(country);
            isUpdated = true;
        }
        if (!Objects.equals(address.getCity().getCityCode(), addressCreateDto.getCityCode())) {
            City city = cityService.findByCityCode(addressCreateDto.getCityCode());
            address.setCity(city);
            isUpdated = true;
        }
        if (!Objects.equals(address.getDistrict().getDistrictId(), addressCreateDto.getDistrictId())) {
            District district = districtService.findByDistrictId(addressCreateDto.getDistrictId());
            address.setDistrict(district);
            isUpdated = true;
        }
        if (!Objects.equals(address.getAddressLine1(), addressCreateDto.getAddressLine1())) {
            address.setAddressLine1(addressCreateDto.getAddressLine1());
            isUpdated = true;
        }
        if (!Objects.equals(address.getUsername(), addressCreateDto.getUsername())) {
            address.setUsername(addressCreateDto.getUsername());
            isUpdated = true;
        }

        if (!Objects.equals(address.getPostalCode(), addressCreateDto.getPostalCode())) {
            address.setPostalCode(addressCreateDto.getPostalCode());
            isUpdated = true;
        }
        if (!Objects.equals(address.getPhoneNo(), addressCreateDto.getPhoneNo())) {
            address.setPhoneNo(addressCreateDto.getPhoneNo());
            isUpdated = true;
        }

        if (!isUpdated) {
            return address;
        }

        return addressRepository.save(address);
    }


    public City findByCityCode(String cityCode) {
        return cityService.findByCityCode(cityCode);
    }

    public District findByDistrictId(Integer districtId) {
        return districtService.findByDistrictId(districtId);
    }


    public Address save(Address address) {
        return addressRepository.save(address);
    }
}
