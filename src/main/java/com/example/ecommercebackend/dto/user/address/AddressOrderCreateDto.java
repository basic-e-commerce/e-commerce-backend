package com.example.ecommercebackend.dto.user.address;

public record AddressOrderCreateDto(String firstName, String lastName, String username, String countryName,
                                    String city, String addressLine1, String addressLine2, String postalCode,
                                    String phoneNo) {
}
