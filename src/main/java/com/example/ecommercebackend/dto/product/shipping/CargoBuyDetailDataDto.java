package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CargoBuyDetailDataDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("amountLocal")
    private String amountLocal;

    @JsonProperty("currencyLocal")
    private String currencyLocal;

    @JsonProperty("amountVat")
    private String amountVat;

    @JsonProperty("amountLocalVat")
    private String amountLocalVat;

    @JsonProperty("amountTax")
    private String amountTax;

    @JsonProperty("amountLocalTax")
    private String amountLocalTax;

    @JsonProperty("totalAmount")
    private String totalAmount;

    @JsonProperty("totalAmountLocal")
    private String totalAmountLocal;

    @JsonProperty("amountOld")
    private String amountOld;

    @JsonProperty("amountLocalOld")
    private String amountLocalOld;

    @JsonProperty("discountRate")
    private String discountRate;

    @JsonProperty("bonusBalance")
    private String bonusBalance;

    @JsonProperty("offerID")
    private String offerID;

    @JsonProperty("shipment")
    private ShipmentBuyDto shipment;

    @JsonProperty("description")
    private String description;

    @JsonProperty("isRefund")
    private boolean isRefund;

    @JsonProperty("isCustomAccountCharge")
    private boolean isCustomAccountCharge;

    @JsonProperty("isPayed")
    private boolean isPayed;

    @JsonProperty("payedVia")
    private String payedVia;

    @JsonProperty("transactionType")
    private String transactionType;

    @JsonProperty("invoiceID")
    private String invoiceID;

    @JsonProperty("cancelDescription")
    private String cancelDescription;

    @JsonProperty("isCanceled")
    private boolean isCanceled;

    @JsonProperty("oldBalance")
    private String oldBalance;

    @JsonProperty("newBalance")
    private String newBalance;

    @JsonProperty("invoiceOldDebt")
    private String invoiceOldDebt;

    @JsonProperty("invoiceNewDebt")
    private String invoiceNewDebt;

    public CargoBuyDetailDataDto(String id, String createdAt, String updatedAt, String amount, String currency, String amountLocal, String currencyLocal, String amountVat, String amountLocalVat, String amountTax, String amountLocalTax, String totalAmount, String totalAmountLocal, String amountOld, String amountLocalOld, String discountRate, String bonusBalance, String offerID, ShipmentBuyDto shipment, String description, boolean isRefund, boolean isCustomAccountCharge, boolean isPayed, String payedVia, String transactionType, String invoiceID, String cancelDescription, boolean isCanceled, String oldBalance, String newBalance, String invoiceOldDebt, String invoiceNewDebt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.amount = amount;
        this.currency = currency;
        this.amountLocal = amountLocal;
        this.currencyLocal = currencyLocal;
        this.amountVat = amountVat;
        this.amountLocalVat = amountLocalVat;
        this.amountTax = amountTax;
        this.amountLocalTax = amountLocalTax;
        this.totalAmount = totalAmount;
        this.totalAmountLocal = totalAmountLocal;
        this.amountOld = amountOld;
        this.amountLocalOld = amountLocalOld;
        this.discountRate = discountRate;
        this.bonusBalance = bonusBalance;
        this.offerID = offerID;
        this.shipment = shipment;
        this.description = description;
        this.isRefund = isRefund;
        this.isCustomAccountCharge = isCustomAccountCharge;
        this.isPayed = isPayed;
        this.payedVia = payedVia;
        this.transactionType = transactionType;
        this.invoiceID = invoiceID;
        this.cancelDescription = cancelDescription;
        this.isCanceled = isCanceled;
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
        this.invoiceOldDebt = invoiceOldDebt;
        this.invoiceNewDebt = invoiceNewDebt;
    }

    public CargoBuyDetailDataDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmountLocal() {
        return amountLocal;
    }

    public void setAmountLocal(String amountLocal) {
        this.amountLocal = amountLocal;
    }

    public String getCurrencyLocal() {
        return currencyLocal;
    }

    public void setCurrencyLocal(String currencyLocal) {
        this.currencyLocal = currencyLocal;
    }

    public String getAmountVat() {
        return amountVat;
    }

    public void setAmountVat(String amountVat) {
        this.amountVat = amountVat;
    }

    public String getAmountLocalVat() {
        return amountLocalVat;
    }

    public void setAmountLocalVat(String amountLocalVat) {
        this.amountLocalVat = amountLocalVat;
    }

    public String getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(String amountTax) {
        this.amountTax = amountTax;
    }

    public String getAmountLocalTax() {
        return amountLocalTax;
    }

    public void setAmountLocalTax(String amountLocalTax) {
        this.amountLocalTax = amountLocalTax;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmountLocal() {
        return totalAmountLocal;
    }

    public void setTotalAmountLocal(String totalAmountLocal) {
        this.totalAmountLocal = totalAmountLocal;
    }

    public String getAmountOld() {
        return amountOld;
    }

    public void setAmountOld(String amountOld) {
        this.amountOld = amountOld;
    }

    public String getAmountLocalOld() {
        return amountLocalOld;
    }

    public void setAmountLocalOld(String amountLocalOld) {
        this.amountLocalOld = amountLocalOld;
    }

    public String getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(String discountRate) {
        this.discountRate = discountRate;
    }

    public String getBonusBalance() {
        return bonusBalance;
    }

    public void setBonusBalance(String bonusBalance) {
        this.bonusBalance = bonusBalance;
    }

    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(String offerID) {
        this.offerID = offerID;
    }

    public ShipmentBuyDto getShipment() {
        return shipment;
    }

    public void setShipment(ShipmentBuyDto shipment) {
        this.shipment = shipment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRefund() {
        return isRefund;
    }

    public void setRefund(boolean refund) {
        isRefund = refund;
    }

    public boolean isCustomAccountCharge() {
        return isCustomAccountCharge;
    }

    public void setCustomAccountCharge(boolean customAccountCharge) {
        isCustomAccountCharge = customAccountCharge;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

    public String getPayedVia() {
        return payedVia;
    }

    public void setPayedVia(String payedVia) {
        this.payedVia = payedVia;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getCancelDescription() {
        return cancelDescription;
    }

    public void setCancelDescription(String cancelDescription) {
        this.cancelDescription = cancelDescription;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public String getOldBalance() {
        return oldBalance;
    }

    public void setOldBalance(String oldBalance) {
        this.oldBalance = oldBalance;
    }

    public String getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(String newBalance) {
        this.newBalance = newBalance;
    }

    public String getInvoiceOldDebt() {
        return invoiceOldDebt;
    }

    public void setInvoiceOldDebt(String invoiceOldDebt) {
        this.invoiceOldDebt = invoiceOldDebt;
    }

    public String getInvoiceNewDebt() {
        return invoiceNewDebt;
    }

    public void setInvoiceNewDebt(String invoiceNewDebt) {
        this.invoiceNewDebt = invoiceNewDebt;
    }
}
