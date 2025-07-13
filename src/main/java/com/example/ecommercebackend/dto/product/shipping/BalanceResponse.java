package com.example.ecommercebackend.dto.product.shipping;

import java.math.BigDecimal;

public class BalanceResponse {
    private Boolean result;
    private String additionalMessage;
    private BigDecimal data;
    private String debt;

    public BalanceResponse(Boolean result, String additionalMessage, BigDecimal data, String debt) {
        this.result = result;
        this.additionalMessage = additionalMessage;
        this.data = data;
        this.debt = debt;
    }

    public BalanceResponse() {
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getAdditionalMessage() {
        return additionalMessage;
    }

    public void setAdditionalMessage(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }

    public BigDecimal getData() {
        return data;
    }

    public void setData(BigDecimal data) {
        this.data = data;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }
}
