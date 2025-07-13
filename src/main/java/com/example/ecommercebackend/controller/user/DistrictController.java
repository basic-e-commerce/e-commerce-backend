package com.example.ecommercebackend.controller.user;

import com.example.ecommercebackend.dto.product.shipping.DistrictDto;
import com.example.ecommercebackend.service.user.DistrictService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/district")
public class DistrictController {
    private final DistrictService districtService;

    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @PostMapping
    public ResponseEntity<DistrictDto> create(@RequestBody(required = false) DistrictDto districtDto) {
        return new ResponseEntity<>(districtService.create(districtDto), HttpStatus.CREATED);
    }

    @GetMapping("/district-id")
    public ResponseEntity<DistrictDto> findByDistrictId(@RequestParam(required = false) Integer districtId) {
        return new ResponseEntity<>(districtService.findByDistrictIdDto(districtId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DistrictDto>> getAll(){
        return new ResponseEntity<>(districtService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/city-code")
    public ResponseEntity<List<DistrictDto>> getDistrictByCityCode(@RequestParam(required = false) String cityCode) {
        return new ResponseEntity<>(districtService.getAllByCityCode(cityCode), HttpStatus.OK);
    }

    @DeleteMapping("/district-id")
    public ResponseEntity<String> deleteBayDistrictId(@RequestParam(required = false) Integer districtId) {
        return new ResponseEntity<>(districtService.deleteByDistrictId(districtId), HttpStatus.OK);
    }


}
