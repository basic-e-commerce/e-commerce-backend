package com.example.ecommercebackend.builder.merchant;

import com.example.ecommercebackend.dto.merchant.merchant.CustomCargoContractUserResponseDataDto;
import com.example.ecommercebackend.dto.product.shipping.CustomCargoContractResponseDataDto;
import com.example.ecommercebackend.dto.product.shipping.CustomCargoContractResponseDto;
import com.example.ecommercebackend.entity.merchant.CustomCargoContract;
import com.example.ecommercebackend.entity.product.order.OrderPackage;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class CustomCargoContractBuilder {
    public CustomCargoContract customCargoContractResponseDataDtoToCustomCargoContract(CustomCargoContractResponseDataDto customCargoContractDataDto) {
        return  new CustomCargoContract(
                customCargoContractDataDto.getId(),
                customCargoContractDataDto.getUsername(),
                customCargoContractDataDto.getName(),
                OrderPackage.CargoCompany.valueOf(customCargoContractDataDto.getProviderCode()),
                customCargoContractDataDto.isActive(),
                customCargoContractDataDto.getParameters(),
                customCargoContractDataDto.getVersion(),
                customCargoContractDataDto.isC2C(),
                customCargoContractDataDto.getIntegrationType(),
                customCargoContractDataDto.getLabelFileType(),
                customCargoContractDataDto.isSharable(),
                customCargoContractDataDto.isPublic(),
                customCargoContractDataDto.isDynamicPrice(),
                Instant.parse(customCargoContractDataDto.getCreatedAt()),
                Instant.parse(customCargoContractDataDto.getUpdatedAt()),
                Instant.parse(customCargoContractDataDto.getPriceUpdatedAt())
        );
    }

    public CustomCargoContractResponseDataDto customCargoContractToCustomCargoContractResponseDataDto(CustomCargoContract customCargoContract) {
        return new CustomCargoContractResponseDataDto(
                customCargoContract.getCargoContractId(),
                customCargoContract.getCreatedAt()
                        .atZone(ZoneId.of("Europe/Istanbul"))
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                customCargoContract.getUpdatedAt()
                        .atZone(ZoneId.of("Europe/Istanbul"))
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                customCargoContract.getCargoCompany().name(),
                customCargoContract.getUsername(),
                customCargoContract.getName(),
                customCargoContract.isActive(),
                customCargoContract.getParameters(),
                customCargoContract.getVersion(),
                customCargoContract.isC2C(),
                customCargoContract.getIntegrationType(),
                customCargoContract.getLabelFileType(),
                customCargoContract.isSharable(),
                customCargoContract.isPublic(),
                customCargoContract.isDynamicPrice(),
                customCargoContract.getPriceUpdatedAt()
                        .atZone(ZoneId.of("Europe/Istanbul"))
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
    }

    public CustomCargoContractUserResponseDataDto customCargoContractToCustomCargoContractUserResponseDataDto(CustomCargoContract customCargoContract,Boolean isDefault) {
        return new CustomCargoContractUserResponseDataDto(
                customCargoContract.getCargoContractId(),
                customCargoContract.getCreatedAt()
                        .atZone(ZoneId.of("Europe/Istanbul"))
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                customCargoContract.getUpdatedAt()
                        .atZone(ZoneId.of("Europe/Istanbul"))
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                customCargoContract.getCargoCompany().name(),
                customCargoContract.getUsername(),
                customCargoContract.getName(),
                customCargoContract.isActive(),
                customCargoContract.getParameters(),
                customCargoContract.getVersion(),
                customCargoContract.isC2C(),
                customCargoContract.getIntegrationType(),
                customCargoContract.getLabelFileType(),
                customCargoContract.isSharable(),
                customCargoContract.isPublic(),
                customCargoContract.isDynamicPrice(),
                customCargoContract.getPriceUpdatedAt()
                        .atZone(ZoneId.of("Europe/Istanbul"))
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                isDefault
        );
    }
}
