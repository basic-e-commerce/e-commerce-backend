package com.example.ecommercebackend.builder.payment;


import com.example.ecommercebackend.dto.payment.response.InstallmentDetailDto;
import com.example.ecommercebackend.dto.payment.response.InstallmentInfoDto;
import com.example.ecommercebackend.dto.payment.response.InstallmentPriceDto;
import com.iyzipay.model.InstallmentDetail;
import com.iyzipay.model.InstallmentInfo;
import com.iyzipay.model.InstallmentPrice;

import java.util.stream.Collectors;


public class PaymentBuilder {

    public static InstallmentInfoDto installmentInfoDto(InstallmentInfo installmentInfo) {
        return new InstallmentInfoDto(
                installmentInfo.getStatus(),
                installmentInfo.getErrorCode(),
                installmentInfo.getErrorMessage(),
                installmentInfo.getErrorGroup(),
                installmentInfo.getLocale(),
                installmentInfo.getSystemTime(),
                installmentInfo.getConversationId(),
                installmentInfo.getInstallmentDetails().stream().map(PaymentBuilder::installmentDetailDto).collect(Collectors.toList())
        );
    }

    public static InstallmentDetailDto installmentDetailDto(InstallmentDetail installmentDetail) {
        return new InstallmentDetailDto(
                installmentDetail.getBinNumber(),
                installmentDetail.getPrice(),
                installmentDetail.getCardType(),
                installmentDetail.getCardAssociation(),
                installmentDetail.getCardFamilyName(),
                installmentDetail.getForce3ds(),
                installmentDetail.getBankCode(),
                installmentDetail.getBankName(),
                installmentDetail.getForceCvc(),
                installmentDetail.getCommercial(),
                installmentDetail.getInstallmentPrices().stream().map(PaymentBuilder::installmentPriceDto).toList()
        );
    }

    public static InstallmentPriceDto installmentPriceDto(InstallmentPrice installmentPrice) {
        return new InstallmentPriceDto(
                installmentPrice.getInstallmentPrice(),
                installmentPrice.getTotalPrice(),
                installmentPrice.getInstallmentNumber()
        );
    }
}
