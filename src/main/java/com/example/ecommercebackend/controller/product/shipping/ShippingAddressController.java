package com.example.ecommercebackend.controller.product.shipping;

import com.example.ecommercebackend.dto.product.shipping.AddressApiDto;
import com.example.ecommercebackend.dto.product.shipping.CityDto;
import com.example.ecommercebackend.dto.product.shipping.DistrictDto;
import com.example.ecommercebackend.service.product.shipping.ShippingAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shipping-address")
public class ShippingAddressController {
    private final ShippingAddressService shippingAddressService;

    public ShippingAddressController(ShippingAddressService shippingAddressService) {
        this.shippingAddressService = shippingAddressService;
    }

    @GetMapping("/city")
    public ResponseEntity<List<CityDto>> getCities() {
        return new ResponseEntity<>(shippingAddressService.getCities(), HttpStatus.OK);
    }

    @GetMapping("/district")
    public ResponseEntity<List<DistrictDto>> getDistricts(@RequestParam(required = false)String cityCode) {
        return new ResponseEntity<>(shippingAddressService.getDistricts(cityCode), HttpStatus.OK);
    }

    @PostMapping("/create-sending-address")
    public ResponseEntity<String> createSendingAddress(@RequestBody(required = false) AddressApiDto addressApiDto){
        return new ResponseEntity<>(shippingAddressService.createSendingAddress(addressApiDto),HttpStatus.CREATED);
    }
}
