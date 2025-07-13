package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.dto.product.shipping.CityDto;
import com.example.ecommercebackend.entity.user.City;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.user.CityRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public CityDto save(@NotNullParam CityDto cityDto) {
        if (cityRepository.existsByCityCode(cityDto.getCityCode())) {
            throw new ResourceAlreadyExistException("Sistemde bu şehir bulunmaktadır!");
        }

        City city = new City(
                cityDto.getName(),
                cityDto.getCityCode(),
                cityDto.getCountryCode()
        );
        City save = cityRepository.save(city);
        return new CityDto(
                save.getName(),
                "",
                save.getCityCode(),
                save.getCountryCode()
        );
    }

    public CityDto findByCityCodeDto(String cityCode) {
        City city = cityRepository.findByCityCode(cityCode).orElseThrow(() -> new NotFoundException("Sehir bulunamadı!"));
        return new CityDto(
                city.getName(),
                "",
                city.getCityCode(),
                city.getCountryCode()
        );
    }
    public City findByCityCode(String cityCode) {
        return cityRepository.findByCityCode(cityCode).orElseThrow(() -> new NotFoundException("Sehir bulunamadı!"));
    }

    public String deleteByCityCode(@NotNullParam String cityCode) {
        if (cityRepository.existsByCityCode(cityCode)) {
            cityRepository.deleteByCityCode(cityCode);
            return "Sehir Silinmiştir";
        }else
            throw new NotFoundException("Sehir Bulunamadı!");
    }

    public List<CityDto> getAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        return cityRepository.findAll(sort).stream().map(x->{
            return new CityDto(
                    x.getName(),
                    "",
                    x.getCityCode(),
                    x.getCountryCode()
            );
        }).toList();
    }
}
