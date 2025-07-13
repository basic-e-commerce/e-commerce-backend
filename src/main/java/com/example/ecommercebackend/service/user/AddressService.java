package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.builder.user.AddressBuilder;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantCreateDto;
import com.example.ecommercebackend.dto.merchant.merchant.MerchantUpdateDto;
import com.example.ecommercebackend.dto.product.shipping.AddressApiDto;
import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Address;
import com.example.ecommercebackend.entity.user.City;
import com.example.ecommercebackend.entity.user.District;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.user.AddressRepository;
import com.example.ecommercebackend.service.product.shipping.CountryService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AddressService {
    private final CountryService countryService;
    private final AddressRepository addressRepository;
    private final AddressBuilder addressBuilder;
    private final CityService cityService;
    private final DistrictService districtService;

    public AddressService(CountryService countryService, AddressRepository addressRepository, AddressBuilder addressBuilder, CityService cityService, DistrictService districtService) {
        this.countryService = countryService;
        this.addressRepository = addressRepository;
        this.addressBuilder = addressBuilder;
        this.cityService = cityService;
        this.districtService = districtService;
    }

    public Address createAddress(AddressCreateDto addressCreateDto) {
        Country country = countryService.findCountryByUpperName(addressCreateDto.getCountryName());
        City city = cityService.findByCityCode(addressCreateDto.getCityCode());
        District district = districtService.findByDistrictId(addressCreateDto.getDistrictId());
        Address address = addressBuilder.addressCreateDtoToAddress(addressCreateDto,country,city,district);
        return addressRepository.save(address);
    }

    public Address createAddress(AddressApiDto addressApiDto, MerchantCreateDto merchantCreateDto) {
        Country country = countryService.findCountryByUpperName("TURKIYE");
        City city = cityService.findByCityCode(addressApiDto.getCityCode());
        District district = districtService.findByDistrictId(addressApiDto.getDistrictID());
        Address address = addressBuilder.addressApiDtoToAddress(addressApiDto,country,merchantCreateDto,city,district);
        return addressRepository.save(address);
    }

    public Address createAddress(AddressApiDto addressApiDto, MerchantUpdateDto merchantCreateDto) {
        Country country = countryService.findCountryByUpperName("TURKIYE");
        City city = cityService.findByCityCode(addressApiDto.getCityCode());
        District district = districtService.findByDistrictId(addressApiDto.getDistrictID());
        Address address = addressBuilder.addressApiDtoToAddress(addressApiDto,country,merchantCreateDto,city,district);
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


}
