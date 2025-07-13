package com.example.ecommercebackend.controller.product.shipping;

import com.example.ecommercebackend.anotation.NotNullParam;
import com.example.ecommercebackend.dto.product.shipping.*;
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

    @PostMapping("/create-receipt-address")
    public ResponseEntity<AddressReceiptDto> createReceiptAddress(@RequestBody(required = false) AddressApiDto addressApiDto){
        return new ResponseEntity<>(shippingAddressService.createReceivingAddress(addressApiDto),HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<AddressShipResponse> getAllShippingAddress(@RequestParam(required = false) Boolean isRecipientAddress,
                                                                     @RequestParam(required = false) Integer limit,
                                                                     @RequestParam(required = false) Integer page){
        return new ResponseEntity<>(shippingAddressService.getAllAddress(isRecipientAddress,limit,page),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShippingAddress(@PathVariable("id") String id){
        return new ResponseEntity<>(shippingAddressService.deleteShippingAddress(id),HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AddressReceiptDto> getShippingAddress(@PathVariable("id") String id){
        return new ResponseEntity<>(shippingAddressService.getShippingAddress(id),HttpStatus.OK);
    }
}
