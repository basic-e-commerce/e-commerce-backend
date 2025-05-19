package com.example.ecommercebackend.controller.user;

import com.example.ecommercebackend.dto.user.address.AddressCreateDto;
import com.example.ecommercebackend.dto.user.address.AddressDetailDto;
import com.example.ecommercebackend.dto.user.customer.CustomerCreateDto;
import com.example.ecommercebackend.dto.user.customer.CustomerProfileDto;
import com.example.ecommercebackend.dto.user.customer.CustomerUpdateDto;
import com.example.ecommercebackend.dto.user.customer.PasswordUpdateDto;
import com.example.ecommercebackend.entity.user.Address;
import com.example.ecommercebackend.entity.user.Customer;
import com.example.ecommercebackend.service.user.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerCreateDto customerCreateDto) {
        return new ResponseEntity<>(customerService.createCustomer(customerCreateDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll(){
        return new ResponseEntity<>(customerService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<CustomerProfileDto> getProfile(){
        return new ResponseEntity<>(customerService.getProfile(),HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateCustomer(@RequestBody(required = false) CustomerUpdateDto customerUpdateDto){
        return new ResponseEntity<>(customerService.updateCustomer(customerUpdateDto),HttpStatus.CREATED);
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody(required = false) PasswordUpdateDto passwordUpdateDto){
        return new ResponseEntity<>(customerService.updatePassword(passwordUpdateDto),HttpStatus.CREATED);
    }

    // ----------- address ------------
    @PostMapping("/address")
    public ResponseEntity<AddressDetailDto> createAddress(@RequestBody AddressCreateDto addressCreateDto) {
        return new ResponseEntity<>(customerService.createAddress(addressCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/address")
    public ResponseEntity<AddressDetailDto> updateAddress(@RequestParam Integer addressId,@RequestBody AddressCreateDto addressCreateDto) {
        return new ResponseEntity<>(customerService.updateAddress(addressId,addressCreateDto),HttpStatus.CREATED);
    }

    @GetMapping("/address")
    public ResponseEntity<Set<AddressDetailDto>> getAllAddresses() {
        return new ResponseEntity<>(customerService.getAddresses(), HttpStatus.OK);
    }

    @DeleteMapping("/address")
    public ResponseEntity<String> deleteAddress(@RequestParam Integer addressId) {
        return new ResponseEntity<>(customerService.deleteAddress(addressId),HttpStatus.OK);
    }











}
