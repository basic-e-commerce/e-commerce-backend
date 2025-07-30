package com.example.ecommercebackend.service.merchant;

import com.example.ecommercebackend.builder.merchant.CustomCargoContractBuilder;
import com.example.ecommercebackend.dto.product.shipping.CustomCargoContractResponseDataDto;
import com.example.ecommercebackend.dto.product.shipping.CustomCargoContractResponseDto;
import com.example.ecommercebackend.entity.merchant.CustomCargoContract;
import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.example.ecommercebackend.repository.merchant.CustomCargoContractRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomCargoContractService {
    private final CustomCargoContractRepository customCargoContractRepository;
    private final CustomCargoContractBuilder customCargoContractBuilder;

    public CustomCargoContractService(CustomCargoContractRepository customCargoContractRepository, CustomCargoContractBuilder customCargoContractBuilder) {
        this.customCargoContractRepository = customCargoContractRepository;
        this.customCargoContractBuilder = customCargoContractBuilder;
    }

    public CustomCargoContract save(CustomCargoContractResponseDataDto customCargoContractDataDto) {
        CustomCargoContract customCargoContract = customCargoContractBuilder.customCargoContractResponseDataDtoToCustomCargoContract(customCargoContractDataDto);
        return customCargoContractRepository.save(customCargoContract);
    }

    public List<CustomCargoContractResponseDataDto> getListCargoContract() {
        return customCargoContractRepository
                .findAll()
                .stream()
                .map(customCargoContractBuilder::customCargoContractToCustomCargoContractResponseDataDto)
                .collect(Collectors.toList());
    }
}
