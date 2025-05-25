package com.example.ecommercebackend.service.product.shipping;

import com.example.ecommercebackend.entity.product.shipping.Country;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.product.shipping.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country findCountryById(int id) {
        return countryRepository.findById(id).orElseThrow(()-> new NotFoundException("Country "+ ExceptionMessage.NOT_FOUND.getMessage()));
    }

    public Country findCountryByIso3(String iso3) {
        return countryRepository.findByIso3(iso3).orElseThrow(()-> new NotFoundException("Country "+ ExceptionMessage.NOT_FOUND.getMessage()));
    }

    public Country findCountryByUpperName(String upperName) {
        return countryRepository.findByUpperName(upperName).orElseThrow(()-> new NotFoundException("Country "+ ExceptionMessage.NOT_FOUND.getMessage()));
    }
}
