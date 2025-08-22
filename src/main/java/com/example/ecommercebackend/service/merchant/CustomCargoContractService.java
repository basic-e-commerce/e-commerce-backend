package com.example.ecommercebackend.service.merchant;

import com.example.ecommercebackend.builder.merchant.CustomCargoContractBuilder;
import com.example.ecommercebackend.dto.merchant.merchant.CustomCargoContractUserResponseDataDto;
import com.example.ecommercebackend.dto.product.shipping.CustomCargoContractResponseDataDto;
import com.example.ecommercebackend.dto.product.shipping.CustomCargoContractResponseDto;
import com.example.ecommercebackend.entity.merchant.CustomCargoContract;
import com.example.ecommercebackend.entity.merchant.Merchant;
import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.example.ecommercebackend.repository.merchant.CustomCargoContractRepository;
import com.example.ecommercebackend.repository.merchant.MerchantRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomCargoContractService {
    private final CustomCargoContractRepository customCargoContractRepository;
    private final CustomCargoContractBuilder customCargoContractBuilder;
    private final MerchantRepository merchantRepository;

    public CustomCargoContractService(CustomCargoContractRepository customCargoContractRepository, CustomCargoContractBuilder customCargoContractBuilder, MerchantRepository merchantRepository) {
        this.customCargoContractRepository = customCargoContractRepository;
        this.customCargoContractBuilder = customCargoContractBuilder;
        this.merchantRepository = merchantRepository;
    }

    public CustomCargoContract save(CustomCargoContractResponseDataDto customCargoContractDataDto) {
        CustomCargoContract customCargoContract = customCargoContractBuilder.customCargoContractResponseDataDtoToCustomCargoContract(customCargoContractDataDto);
        return customCargoContractRepository.save(customCargoContract);
    }

    public CustomCargoContract save(CustomCargoContract customCargoContract) {
        return customCargoContractRepository.save(customCargoContract);
    }

    public List<CustomCargoContractUserResponseDataDto> getListCargoContract() {
        Merchant merchant = merchantRepository.findAll().stream().findFirst().get();
        return customCargoContractRepository
                .findAll()
                .stream()
                .map(x->{
                    if (Objects.equals(x.getCargoContractId(), merchant.getDefaultCustomCargoContract().getCargoContractId())){
                        return customCargoContractBuilder.customCargoContractToCustomCargoContractUserResponseDataDto(x,true);
                    }else
                        return customCargoContractBuilder.customCargoContractToCustomCargoContractUserResponseDataDto(x,false);
                })
                .collect(Collectors.toList());
    }
}
