package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OfferCancelDataDto {
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

    @JsonProperty("length")
    private String length;

    @JsonProperty("width")
    private String width;

    @JsonProperty("height")
    private String height;

    @JsonProperty("desi")
    private String desi;

    @JsonProperty("oldDesi")
    private String oldDesi;

    @JsonProperty("distanceUnit")
    private String distanceUnit;

    @JsonProperty("weight")
    private String weight;

    @JsonProperty("oldWeight")
    private String oldWeight;

    @JsonProperty("massUnit")
    private String massUnit;

    @JsonProperty("useWeightOfItems")
    private Boolean useWeightOfItems;

    @JsonProperty("useDimensionsOfItems")
    private Boolean useDimensionsOfItems;

    @JsonProperty("trackingStatus")
    private String trackingStatus;

    @JsonProperty("labelFileType")
    private String labelFileType;

    @JsonProperty("hidePackageContentOnTag")
    private Boolean hidePackageContentOnTag;

    @JsonProperty("shipmentDate")
    private String shipmentDate;

    @JsonProperty("invoiceGenerated")
    private Boolean invoiceGenerated;

    @JsonProperty("refundInvoiceID")
    private String refundInvoiceID;

    @JsonProperty("productPaymentOnDelivery")
    private Boolean productPaymentOnDelivery;

    @JsonProperty("orderID")
    private String orderID;

    @JsonProperty("order")
    private Object order;

    @JsonProperty("senderAddress")
    private Object senderAddress;

    @JsonProperty("recipientAddress")
    private Object recipientAddress;

    @JsonProperty("createReturnLabel")
    private Boolean createReturnLabel;

    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("offers")
    private Object offers;

    @JsonProperty("acceptedOffer")
    private Object acceptedOffer;

    @JsonProperty("enableAutomation")
    private Boolean enableAutomation;

    @JsonProperty("organizationShipmentID")
    private Integer organizationShipmentID;

    @JsonProperty("providerBranchName")
    private String providerBranchName;

    @JsonProperty("providerInvoiceNo")
    private String providerInvoiceNo;

    @JsonProperty("providerReceiptNo")
    private String providerReceiptNo;

    @JsonProperty("providerSerialNo")
    private String providerSerialNo;

    @JsonProperty("hasError")
    private Boolean hasError;

    @JsonProperty("lastErrorMessage")
    private String lastErrorMessage;

    @JsonProperty("lastErrorCode")
    private String lastErrorCode;

    @JsonProperty("cancelDate")
    private String cancelDate;

    @JsonProperty("isReturned")
    private Boolean isReturned;

    @JsonProperty("isReturn")
    private Boolean isReturn;

    @JsonProperty("isTrackingOnly")
    private Boolean isTrackingOnly;

    @JsonProperty("isRecipientSmsActivated")
    private Boolean isRecipientSmsActivated;

    @JsonProperty("packageAcceptedAt")
    private String packageAcceptedAt;

    @JsonProperty("tenantId")
    private String tenantId;

    public OfferCancelDataDto(String id, String createdAt, String updatedAt, String amount, String currency, String amountLocal, String amountVat, String amountLocalVat, String amountTax, String amountLocalTax, String totalAmount, String totalAmountLocal, String amountOld, String amountLocalOld, String discountRate, String bonusBalance, String length, String width, String height, String desi, String oldDesi, String distanceUnit, String weight, String oldWeight, String massUnit, Boolean useWeightOfItems, Boolean useDimensionsOfItems, String trackingStatus, String labelFileType, Boolean hidePackageContentOnTag, String shipmentDate, Boolean invoiceGenerated, String refundInvoiceID, Boolean productPaymentOnDelivery, String orderID, Object order, Object senderAddress, Object recipientAddress, Boolean createReturnLabel, String statusCode, Object offers, Object acceptedOffer, Boolean enableAutomation, Integer organizationShipmentID, String providerBranchName, String providerInvoiceNo, String providerReceiptNo, String providerSerialNo, Boolean hasError, String lastErrorMessage, String lastErrorCode, String cancelDate, Boolean isReturned, Boolean isReturn, Boolean isTrackingOnly, Boolean isRecipientSmsActivated, String packageAcceptedAt, String tenantId) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.amount = amount;
        this.currency = currency;
        this.amountLocal = amountLocal;
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
        this.length = length;
        this.width = width;
        this.height = height;
        this.desi = desi;
        this.oldDesi = oldDesi;
        this.distanceUnit = distanceUnit;
        this.weight = weight;
        this.oldWeight = oldWeight;
        this.massUnit = massUnit;
        this.useWeightOfItems = useWeightOfItems;
        this.useDimensionsOfItems = useDimensionsOfItems;
        this.trackingStatus = trackingStatus;
        this.labelFileType = labelFileType;
        this.hidePackageContentOnTag = hidePackageContentOnTag;
        this.shipmentDate = shipmentDate;
        this.invoiceGenerated = invoiceGenerated;
        this.refundInvoiceID = refundInvoiceID;
        this.productPaymentOnDelivery = productPaymentOnDelivery;
        this.orderID = orderID;
        this.order = order;
        this.senderAddress = senderAddress;
        this.recipientAddress = recipientAddress;
        this.createReturnLabel = createReturnLabel;
        this.statusCode = statusCode;
        this.offers = offers;
        this.acceptedOffer = acceptedOffer;
        this.enableAutomation = enableAutomation;
        this.organizationShipmentID = organizationShipmentID;
        this.providerBranchName = providerBranchName;
        this.providerInvoiceNo = providerInvoiceNo;
        this.providerReceiptNo = providerReceiptNo;
        this.providerSerialNo = providerSerialNo;
        this.hasError = hasError;
        this.lastErrorMessage = lastErrorMessage;
        this.lastErrorCode = lastErrorCode;
        this.cancelDate = cancelDate;
        this.isReturned = isReturned;
        this.isReturn = isReturn;
        this.isTrackingOnly = isTrackingOnly;
        this.isRecipientSmsActivated = isRecipientSmsActivated;
        this.packageAcceptedAt = packageAcceptedAt;
        this.tenantId = tenantId;
    }

    public OfferCancelDataDto() {
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getDesi() {
        return desi;
    }

    public void setDesi(String desi) {
        this.desi = desi;
    }

    public String getOldDesi() {
        return oldDesi;
    }

    public void setOldDesi(String oldDesi) {
        this.oldDesi = oldDesi;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getOldWeight() {
        return oldWeight;
    }

    public void setOldWeight(String oldWeight) {
        this.oldWeight = oldWeight;
    }

    public String getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(String massUnit) {
        this.massUnit = massUnit;
    }

    public Boolean getUseWeightOfItems() {
        return useWeightOfItems;
    }

    public void setUseWeightOfItems(Boolean useWeightOfItems) {
        this.useWeightOfItems = useWeightOfItems;
    }

    public Boolean getUseDimensionsOfItems() {
        return useDimensionsOfItems;
    }

    public void setUseDimensionsOfItems(Boolean useDimensionsOfItems) {
        this.useDimensionsOfItems = useDimensionsOfItems;
    }

    public String getTrackingStatus() {
        return trackingStatus;
    }

    public void setTrackingStatus(String trackingStatus) {
        this.trackingStatus = trackingStatus;
    }

    public String getLabelFileType() {
        return labelFileType;
    }

    public void setLabelFileType(String labelFileType) {
        this.labelFileType = labelFileType;
    }

    public Boolean getHidePackageContentOnTag() {
        return hidePackageContentOnTag;
    }

    public void setHidePackageContentOnTag(Boolean hidePackageContentOnTag) {
        this.hidePackageContentOnTag = hidePackageContentOnTag;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public Boolean getInvoiceGenerated() {
        return invoiceGenerated;
    }

    public void setInvoiceGenerated(Boolean invoiceGenerated) {
        this.invoiceGenerated = invoiceGenerated;
    }

    public String getRefundInvoiceID() {
        return refundInvoiceID;
    }

    public void setRefundInvoiceID(String refundInvoiceID) {
        this.refundInvoiceID = refundInvoiceID;
    }

    public Boolean getProductPaymentOnDelivery() {
        return productPaymentOnDelivery;
    }

    public void setProductPaymentOnDelivery(Boolean productPaymentOnDelivery) {
        this.productPaymentOnDelivery = productPaymentOnDelivery;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Object getOrder() {
        return order;
    }

    public void setOrder(Object order) {
        this.order = order;
    }

    public Object getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(Object senderAddress) {
        this.senderAddress = senderAddress;
    }

    public Object getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(Object recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public Boolean getCreateReturnLabel() {
        return createReturnLabel;
    }

    public void setCreateReturnLabel(Boolean createReturnLabel) {
        this.createReturnLabel = createReturnLabel;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Object getOffers() {
        return offers;
    }

    public void setOffers(Object offers) {
        this.offers = offers;
    }

    public Object getAcceptedOffer() {
        return acceptedOffer;
    }

    public void setAcceptedOffer(Object acceptedOffer) {
        this.acceptedOffer = acceptedOffer;
    }

    public Boolean getEnableAutomation() {
        return enableAutomation;
    }

    public void setEnableAutomation(Boolean enableAutomation) {
        this.enableAutomation = enableAutomation;
    }

    public Integer getOrganizationShipmentID() {
        return organizationShipmentID;
    }

    public void setOrganizationShipmentID(Integer organizationShipmentID) {
        this.organizationShipmentID = organizationShipmentID;
    }

    public String getProviderBranchName() {
        return providerBranchName;
    }

    public void setProviderBranchName(String providerBranchName) {
        this.providerBranchName = providerBranchName;
    }

    public String getProviderInvoiceNo() {
        return providerInvoiceNo;
    }

    public void setProviderInvoiceNo(String providerInvoiceNo) {
        this.providerInvoiceNo = providerInvoiceNo;
    }

    public String getProviderReceiptNo() {
        return providerReceiptNo;
    }

    public void setProviderReceiptNo(String providerReceiptNo) {
        this.providerReceiptNo = providerReceiptNo;
    }

    public String getProviderSerialNo() {
        return providerSerialNo;
    }

    public void setProviderSerialNo(String providerSerialNo) {
        this.providerSerialNo = providerSerialNo;
    }

    public Boolean getHasError() {
        return hasError;
    }

    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }

    public void setLastErrorMessage(String lastErrorMessage) {
        this.lastErrorMessage = lastErrorMessage;
    }

    public String getLastErrorCode() {
        return lastErrorCode;
    }

    public void setLastErrorCode(String lastErrorCode) {
        this.lastErrorCode = lastErrorCode;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Boolean getReturned() {
        return isReturned;
    }

    public void setReturned(Boolean returned) {
        isReturned = returned;
    }

    public Boolean getReturn() {
        return isReturn;
    }

    public void setReturn(Boolean aReturn) {
        isReturn = aReturn;
    }

    public Boolean getTrackingOnly() {
        return isTrackingOnly;
    }

    public void setTrackingOnly(Boolean trackingOnly) {
        isTrackingOnly = trackingOnly;
    }

    public Boolean getRecipientSmsActivated() {
        return isRecipientSmsActivated;
    }

    public void setRecipientSmsActivated(Boolean recipientSmsActivated) {
        isRecipientSmsActivated = recipientSmsActivated;
    }

    public String getPackageAcceptedAt() {
        return packageAcceptedAt;
    }

    public void setPackageAcceptedAt(String packageAcceptedAt) {
        this.packageAcceptedAt = packageAcceptedAt;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
