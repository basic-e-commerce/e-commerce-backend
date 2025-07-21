package com.example.ecommercebackend.dto.user.address;

import com.example.ecommercebackend.anotation.NotNullField;

public record AddressOrderCreateDto(@NotNullField String firstName,
                                    @NotNullField String lastName,
                                    @NotNullField String username,
                                    @NotNullField String countryName,
                                    @NotNullField String countryIso,
                                    @NotNullField String city,
                                    @NotNullField String cityCode,
                                    @NotNullField String district,
                                    @NotNullField Integer districtId,
                                    @NotNullField String addressLine1,
                                    @NotNullField String postalCode,
                                    @NotNullField String phoneNo,
                                    String geliverId) {
}
