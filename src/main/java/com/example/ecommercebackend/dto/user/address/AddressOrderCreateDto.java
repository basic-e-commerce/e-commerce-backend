package com.example.ecommercebackend.dto.user.address;

import com.example.ecommercebackend.anotation.NotNullField;
import com.example.ecommercebackend.anotation.ValidPhoneNumber;

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
                                    @NotNullField @ValidPhoneNumber String phoneNo,
                                    String geliverId) {
}
