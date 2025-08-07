package com.example.ecommercebackend.controller.product.order;

import com.example.ecommercebackend.dto.product.shipping.CustomCargoContractResponseDataDto;
import com.example.ecommercebackend.service.merchant.CustomCargoContractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/custom-cargo-contract")
public class CustomCargoContractController {
    private final CustomCargoContractService service;

    public CustomCargoContractController(CustomCargoContractService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CustomCargoContractResponseDataDto>> getAll() {
        return new ResponseEntity<>(service.getListCargoContract(), HttpStatus.OK);
    }
}
