package com.example.ecommercebackend.dto.product.shipping;

import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CargoRefundDto {
    @JsonProperty("isReturn")
    private boolean isReturn;

    @JsonProperty("willAccept")
    private boolean willAccept;

    @JsonProperty("providerServiceCode")
    private OrderPackage.CargoCompany providerServiceCode;

    @JsonProperty("count")
    private int count;

    @JsonProperty("senderAddress")
    private ShippingSenderAddress senderAddress;

    public CargoRefundDto(boolean isReturn, boolean willAccept, OrderPackage.CargoCompany providerServiceCode, int count, ShippingSenderAddress senderAddress) {
        this.isReturn = isReturn;
        this.willAccept = willAccept;
        this.providerServiceCode = providerServiceCode;
        this.count = count;
        this.senderAddress = senderAddress;
    }

    public CargoRefundDto() {
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }

    public boolean isWillAccept() {
        return willAccept;
    }

    public void setWillAccept(boolean willAccept) {
        this.willAccept = willAccept;
    }

    public OrderPackage.CargoCompany getProviderServiceCode() {
        return providerServiceCode;
    }

    public void setProviderServiceCode(OrderPackage.CargoCompany providerServiceCode) {
        this.providerServiceCode = providerServiceCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ShippingSenderAddress getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(ShippingSenderAddress senderAddress) {
        this.senderAddress = senderAddress;
    }
}
