package com.example.ecommercebackend.controller.product.shipping;

import com.example.ecommercebackend.dto.product.shipping.CargoOffer;
import com.example.ecommercebackend.dto.product.shipping.ShippingTemplateCreateDto;
import com.example.ecommercebackend.dto.product.shipping.ShippingTemplateDto;
import com.example.ecommercebackend.entity.product.shipping.ShippingTemplate;
import com.example.ecommercebackend.service.product.shipping.ShippingTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shipping-template")
public class ShippingTemplateController {

    private final ShippingTemplateService shippingTemplateService;

    public ShippingTemplateController(ShippingTemplateService shippingTemplateService) {
        this.shippingTemplateService = shippingTemplateService;
    }

    @PostMapping
    public ResponseEntity<ShippingTemplate> create(@RequestBody(required = false) ShippingTemplateCreateDto shippingTemplateCreateDto) {
        return new ResponseEntity<>(shippingTemplateService.createShippingTemplate(shippingTemplateCreateDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ShippingTemplateDto>> getAll() {
        return new ResponseEntity<>(shippingTemplateService.getList(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestParam(required = false) String geliverId){
        return new ResponseEntity<>(shippingTemplateService.deleteShippingTemplate(geliverId), HttpStatus.OK);
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(){
        return new ResponseEntity<>(shippingTemplateService.getBalance(),HttpStatus.OK);
    }

    @GetMapping("/offer")
    public ResponseEntity<List<CargoOffer>> getCargoOffer(@RequestParam(required = false) String length,
                                                          @RequestParam(required = false) String width,
                                                          @RequestParam(required = false) String height,
                                                          @RequestParam(required = false) String weight
                                                          ){
        return new ResponseEntity<>(shippingTemplateService.getCargoOffer(length, width, height, weight), HttpStatus.OK);
    }
}
