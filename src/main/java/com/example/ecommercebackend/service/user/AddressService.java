package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.builder.user.AddressBuilder;
import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.entity.user.Address;
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

    public AddressService(CountryService countryService, AddressRepository addressRepository, AddressBuilder addressBuilder) {
        this.countryService = countryService;
        this.addressRepository = addressRepository;
        this.addressBuilder = addressBuilder;
    }

    public Address createAddress(AddressCreateDto addressCreateDto) {
        Country country = countryService.findCountryByIso3(addressCreateDto.getCountryName());
        Address address = addressBuilder.addressCreateDtoToAddress(addressCreateDto,country);
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
        Country country = countryService.findCountryByIso3(addressCreateDto.getCountryName());

        boolean isUpdated = false;

        if (!Objects.equals(address.getTitle(), addressCreateDto.getTitle())) {
            address.setTitle(addressCreateDto.getTitle());
            isUpdated = true;
        }
        if (!Objects.equals(address.getCountry().getIso3(), addressCreateDto.getCountryName())) {
            address.setCountry(country);
            isUpdated = true;
        }
        if (!Objects.equals(address.getCity(), addressCreateDto.getCity())) {
            address.setCity(addressCreateDto.getCity());
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





}
