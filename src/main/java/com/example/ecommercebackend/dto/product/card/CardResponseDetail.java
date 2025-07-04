package com.example.ecommercebackend.dto.product.card;

import com.example.ecommercebackend.dto.product.coupon.CouponCustomerResponseDto;
import com.example.ecommercebackend.dto.product.coupon.CouponResponseDto;

import java.math.BigDecimal;
import java.util.List;

public class CardResponseDetail {
    private BigDecimal totalWithOutTax;
    private BigDecimal totalTax;
    private BigDecimal shippingCost;
    private BigDecimal totalPrice;
    private float shippingCostRate;
    List<CardResponseDetails> details;
    private CouponCustomerResponseDto couponCustomerResponseDto;

    public CardResponseDetail(BigDecimal totalWithOutTax, BigDecimal totalTax, BigDecimal shippingCost, BigDecimal totalPrice, float shippingCostRate, List<CardResponseDetails> details, CouponCustomerResponseDto couponCustomerResponseDto) {
        this.totalWithOutTax = totalWithOutTax;
        this.totalTax = totalTax;
        this.shippingCost = shippingCost;
        this.totalPrice = totalPrice;
        this.shippingCostRate = shippingCostRate;
        this.details = details;
        this.couponCustomerResponseDto = couponCustomerResponseDto;
    }

    public BigDecimal getTotalWithOutTax() {
        return totalWithOutTax;
    }

    public void setTotalWithOutTax(BigDecimal totalWithOutTax) {
        this.totalWithOutTax = totalWithOutTax;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getShippingCostRate() {
        return shippingCostRate;
    }

    public void setShippingCostRate(float shippingCostRate) {
        this.shippingCostRate = shippingCostRate;
    }

    public List<CardResponseDetails> getDetails() {
        return details;
    }

    public void setDetails(List<CardResponseDetails> details) {
        this.details = details;
    }


    public CouponCustomerResponseDto getCouponCustomerResponseDto() {
        return couponCustomerResponseDto;
    }

    public void setCouponCustomerResponseDto(CouponCustomerResponseDto couponCustomerResponseDto) {
        this.couponCustomerResponseDto = couponCustomerResponseDto;
    }
}
