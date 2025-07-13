package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.dto.product.shipping.DistrictDto;
import com.example.ecommercebackend.entity.user.District;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.exception.ResourceAlreadyExistException;
import com.example.ecommercebackend.repository.user.DistrictRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService {
    private final DistrictRepository districtRepository;

    public DistrictService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }

    public DistrictDto create(@NotNullParam DistrictDto districtDto) {
        if (existByDistrictId(districtDto.getDistrictID()))
            throw new ResourceAlreadyExistException("Sistemde bu İlçe kayıtlıdır");

        District district = new District(
                districtDto.getName(),
                districtDto.getDistrictID(),
                districtDto.getCityCode(),
                districtDto.getCountryCode()
        );
        District savedDistrict = districtRepository.save(district);
        return new DistrictDto(
                savedDistrict.getName(),
                savedDistrict.getDistrictId(),
                savedDistrict.getCityCode(),
                "",
                savedDistrict.getCountryCode()
        );
    }

    public DistrictDto findByDistrictIdDto(@NotNullParam Integer districtId) {
        District district = districtRepository.findByDistrictId(districtId).orElseThrow(() -> new NotFoundException("Ilce bulunamadı!"));
        return new DistrictDto(
                district.getName(),
                district.getDistrictId(),
                district.getCityCode(),
                "",
                district.getCountryCode()
        );
    }

    public District findByDistrictId(@NotNullParam Integer districtId) {
        return districtRepository.findByDistrictId(districtId).orElseThrow(() -> new NotFoundException("Ilce bulunamadı!"));
    }

    public List<DistrictDto> getAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        return districtRepository.findAll(sort).stream().map(x->{
            return new DistrictDto(
                    x.getName(),
                    x.getDistrictId(),
                    x.getCityCode(),
                    "",
                    x.getCountryCode()
            );
        }).toList();
    }

    public List<DistrictDto> getAllByCityCode(@NotNullParam String cityCode) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        return districtRepository.findAllByCityCode(cityCode,sort).stream().map(x->{
            return new DistrictDto(
                    x.getName(),
                    x.getDistrictId(),
                    x.getCityCode(),
                    "",
                    x.getCountryCode()
            );
        }).toList();
    }

    public String deleteByDistrictId(@NotNullParam Integer districtId) {
        if (existByDistrictId(districtId)){
            districtRepository.deleteByDistrictId(districtId);
            return "Ilce Silindi";
        }else
            throw new NotFoundException("Ilce Bulunamadı!");
    }

    public Boolean existByDistrictId(Integer districtId) {
        return districtRepository.existsByDistrictId(districtId);
    }






}
