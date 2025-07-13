package com.example.ecommercebackend.controller.user;

import com.example.ecommercebackend.dto.product.shipping.CityDto;
import com.example.ecommercebackend.service.user.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<CityDto>> getAll(){
        return new ResponseEntity<>(cityService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CityDto> create(@RequestBody(required = false) CityDto cityDto){
        return new ResponseEntity<>(cityService.save(cityDto),HttpStatus.CREATED);
    }

    @GetMapping("/code")
    public ResponseEntity<CityDto> getByCode(@RequestParam(required = false) String cityCode){
        return new ResponseEntity<>(cityService.findByCityCodeDto(cityCode),HttpStatus.OK);
    }

    @DeleteMapping("/code")
    public ResponseEntity<String> deleteByCityCode(@RequestParam(required = false) String cityCode){
        return new ResponseEntity<>(cityService.deleteByCityCode(cityCode),HttpStatus.OK);
    }
}
