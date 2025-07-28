package com.example.ecommercebackend.controller.product.shipping;

import com.example.ecommercebackend.dto.product.shipping.*;
import com.example.ecommercebackend.service.product.shipping.ShippingCargoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipping-cargo")
public class ShippingCargoController {
    private final ShippingCargoService shippingCargoService;

    public ShippingCargoController(ShippingCargoService shippingCargoService) {
        this.shippingCargoService = shippingCargoService;
    }

    @PostMapping
    public ResponseEntity<CargoOfferResponseDto> getCreateCargoOffers(@RequestBody CargoOfferRequestDto requestDto) {
        return new ResponseEntity<>(shippingCargoService.getCreateCargoOffers(requestDto), HttpStatus.CREATED);
    }

    @PostMapping("/offer-approve")
    public ResponseEntity<OfferApproveDto> offerApprove(@RequestParam(required = false) String id){
        return new ResponseEntity<>(shippingCargoService.offerApprove(id),HttpStatus.OK);
    }

    @PostMapping("/offer-cancel")
    public ResponseEntity<OfferCancelDto> offerCancel(@RequestParam(required = false)String id){
        return new ResponseEntity<>(shippingCargoService.offerCancel(id),HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<CargoDetailDto> getDetail(@RequestParam(required = false) String id){
        return new ResponseEntity<>(shippingCargoService.getCargoDetail(id),HttpStatus.OK);
    }

    @PutMapping("/refund")
    public ResponseEntity<CargoBuyDetailDto> getCargoRefund(@RequestParam(required = false) String id,@RequestBody(required = false) CargoRefundDto requestDto){
        return new ResponseEntity<>(shippingCargoService.getCargoRefund(id,requestDto),HttpStatus.OK);
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<CargoCancelDetailDto> getCargoCancel(@RequestParam(required = false) String id){
        return new ResponseEntity<>(shippingCargoService.cancelCargoShipment(id),HttpStatus.OK);
    }
}
