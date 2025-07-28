package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

public class CargoCancelDetailDataDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("amountLocal")
    private BigDecimal amountLocal;

    @JsonProperty("amountVat")
    private BigDecimal amountVat;

    @JsonProperty("amountLocalVat")
    private BigDecimal amountLocalVat;

    @JsonProperty("amountTax")
    private BigDecimal amountTax;

    @JsonProperty("amountLocalTax")
    private BigDecimal amountLocalTax;

    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;

    @JsonProperty("totalAmountLocal")
    private BigDecimal totalAmountLocal;

    @JsonProperty("amountOld")
    private BigDecimal amountOld;

    @JsonProperty("amountLocalOld")
    private BigDecimal amountLocalOld;

    @JsonProperty("discountRate")
    private BigDecimal discountRate;

    @JsonProperty("bonusBalance")
    private BigDecimal bonusBalance;

    @JsonProperty("length")
    private BigDecimal length;

    @JsonProperty("width")
    private BigDecimal width;

    @JsonProperty("height")
    private BigDecimal height;

    @JsonProperty("desi")
    private BigDecimal desi;

    @JsonProperty("oldDesi")
    private BigDecimal oldDesi;

    @JsonProperty("distanceUnit")
    private String distanceUnit;

    @JsonProperty("weight")
    private BigDecimal weight;

    @JsonProperty("oldWeight")
    private BigDecimal oldWeight;

    @JsonProperty("massUnit")
    private String massUnit;

    @JsonProperty("useWeightOfItems")
    private boolean useWeightOfItems;

    @JsonProperty("useDimensionsOfItems")
    private boolean useDimensionsOfItems;

    @JsonProperty("trackingStatus")
    private String trackingStatus;

    @JsonProperty("labelFileType")
    private String labelFileType;

    @JsonProperty("hidePackageContentOnTag")
    private boolean hidePackageContentOnTag;

    @JsonProperty("shipmentDate")
    private String shipmentDate;

    @JsonProperty("invoiceGenerated")
    private boolean invoiceGenerated;

    @JsonProperty("refundInvoiceID")
    private String refundInvoiceID;

    @JsonProperty("productPaymentOnDelivery")
    private boolean productPaymentOnDelivery;

    @JsonProperty("orderID")
    private String orderID;

    @JsonProperty("order")
    private String order;

    @JsonProperty("senderAddress")
    private String senderAddress;

    @JsonProperty("recipientAddress")
    private String recipientAddress;

    @JsonProperty("createReturnLabel")
    private boolean createReturnLabel;

    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("offers")
    private String offers;

    @JsonProperty("acceptedOffer")
    private String acceptedOffer;

    @JsonProperty("enableAutomation")
    private boolean enableAutomation;

    @JsonProperty("organizationShipmentID")
    private long organizationShipmentID;

    @JsonProperty("providerBranchName")
    private String providerBranchName;

    @JsonProperty("providerInvoiceNo")
    private String providerInvoiceNo;

    @JsonProperty("providerReceiptNo")
    private String providerReceiptNo;

    @JsonProperty("providerSerialNo")
    private String providerSerialNo;

    @JsonProperty("hasError")
    private boolean hasError;

    @JsonProperty("lastErrorMessage")
    private String lastErrorMessage;

    @JsonProperty("lastErrorCode")
    private String lastErrorCode;

    @JsonProperty("cancelDate")
    private String cancelDate;

    @JsonProperty("isReturned")
    private boolean isReturned;

    @JsonProperty("isReturn")
    private boolean isReturn;

    @JsonProperty("isTrackingOnly")
    private boolean isTrackingOnly;

    @JsonProperty("isRecipientSmsActivated")
    private Boolean isRecipientSmsActivated;

    @JsonProperty("packageAcceptedAt")
    private String packageAcceptedAt;

    @JsonProperty("tenantId")
    private String tenantId;

    public CargoCancelDetailDataDto(String id, String createdAt, String updatedAt, BigDecimal amount, String currency, BigDecimal amountLocal, BigDecimal amountVat, BigDecimal amountLocalVat, BigDecimal amountTax, BigDecimal amountLocalTax, BigDecimal totalAmount, BigDecimal totalAmountLocal, BigDecimal amountOld, BigDecimal amountLocalOld, BigDecimal discountRate, BigDecimal bonusBalance, BigDecimal length, BigDecimal width, BigDecimal height, BigDecimal desi, BigDecimal oldDesi, String distanceUnit, BigDecimal weight, BigDecimal oldWeight, String massUnit, boolean useWeightOfItems, boolean useDimensionsOfItems, String trackingStatus, String labelFileType, boolean hidePackageContentOnTag, String shipmentDate, boolean invoiceGenerated, String refundInvoiceID, boolean productPaymentOnDelivery, String orderID, String order, String senderAddress, String recipientAddress, boolean createReturnLabel, String statusCode, String offers, String acceptedOffer, boolean enableAutomation, long organizationShipmentID, String providerBranchName, String providerInvoiceNo, String providerReceiptNo, String providerSerialNo, boolean hasError, String lastErrorMessage, String lastErrorCode, String cancelDate, boolean isReturned, boolean isReturn, boolean isTrackingOnly, Boolean isRecipientSmsActivated, String packageAcceptedAt, String tenantId) {
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

    public CargoCancelDetailDataDto() {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmountLocal() {
        return amountLocal;
    }

    public void setAmountLocal(BigDecimal amountLocal) {
        this.amountLocal = amountLocal;
    }

    public BigDecimal getAmountVat() {
        return amountVat;
    }

    public void setAmountVat(BigDecimal amountVat) {
        this.amountVat = amountVat;
    }

    public BigDecimal getAmountLocalVat() {
        return amountLocalVat;
    }

    public void setAmountLocalVat(BigDecimal amountLocalVat) {
        this.amountLocalVat = amountLocalVat;
    }

    public BigDecimal getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(BigDecimal amountTax) {
        this.amountTax = amountTax;
    }

    public BigDecimal getAmountLocalTax() {
        return amountLocalTax;
    }

    public void setAmountLocalTax(BigDecimal amountLocalTax) {
        this.amountLocalTax = amountLocalTax;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmountLocal() {
        return totalAmountLocal;
    }

    public void setTotalAmountLocal(BigDecimal totalAmountLocal) {
        this.totalAmountLocal = totalAmountLocal;
    }

    public BigDecimal getAmountOld() {
        return amountOld;
    }

    public void setAmountOld(BigDecimal amountOld) {
        this.amountOld = amountOld;
    }

    public BigDecimal getAmountLocalOld() {
        return amountLocalOld;
    }

    public void setAmountLocalOld(BigDecimal amountLocalOld) {
        this.amountLocalOld = amountLocalOld;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getBonusBalance() {
        return bonusBalance;
    }

    public void setBonusBalance(BigDecimal bonusBalance) {
        this.bonusBalance = bonusBalance;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getDesi() {
        return desi;
    }

    public void setDesi(BigDecimal desi) {
        this.desi = desi;
    }

    public BigDecimal getOldDesi() {
        return oldDesi;
    }

    public void setOldDesi(BigDecimal oldDesi) {
        this.oldDesi = oldDesi;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getOldWeight() {
        return oldWeight;
    }

    public void setOldWeight(BigDecimal oldWeight) {
        this.oldWeight = oldWeight;
    }

    public String getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(String massUnit) {
        this.massUnit = massUnit;
    }

    public boolean isUseWeightOfItems() {
        return useWeightOfItems;
    }

    public void setUseWeightOfItems(boolean useWeightOfItems) {
        this.useWeightOfItems = useWeightOfItems;
    }

    public boolean isUseDimensionsOfItems() {
        return useDimensionsOfItems;
    }

    public void setUseDimensionsOfItems(boolean useDimensionsOfItems) {
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

    public boolean isHidePackageContentOnTag() {
        return hidePackageContentOnTag;
    }

    public void setHidePackageContentOnTag(boolean hidePackageContentOnTag) {
        this.hidePackageContentOnTag = hidePackageContentOnTag;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public boolean isInvoiceGenerated() {
        return invoiceGenerated;
    }

    public void setInvoiceGenerated(boolean invoiceGenerated) {
        this.invoiceGenerated = invoiceGenerated;
    }

    public String getRefundInvoiceID() {
        return refundInvoiceID;
    }

    public void setRefundInvoiceID(String refundInvoiceID) {
        this.refundInvoiceID = refundInvoiceID;
    }

    public boolean isProductPaymentOnDelivery() {
        return productPaymentOnDelivery;
    }

    public void setProductPaymentOnDelivery(boolean productPaymentOnDelivery) {
        this.productPaymentOnDelivery = productPaymentOnDelivery;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public boolean isCreateReturnLabel() {
        return createReturnLabel;
    }

    public void setCreateReturnLabel(boolean createReturnLabel) {
        this.createReturnLabel = createReturnLabel;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public String getAcceptedOffer() {
        return acceptedOffer;
    }

    public void setAcceptedOffer(String acceptedOffer) {
        this.acceptedOffer = acceptedOffer;
    }

    public boolean isEnableAutomation() {
        return enableAutomation;
    }

    public void setEnableAutomation(boolean enableAutomation) {
        this.enableAutomation = enableAutomation;
    }

    public long getOrganizationShipmentID() {
        return organizationShipmentID;
    }

    public void setOrganizationShipmentID(long organizationShipmentID) {
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

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
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

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }

    public boolean isTrackingOnly() {
        return isTrackingOnly;
    }

    public void setTrackingOnly(boolean trackingOnly) {
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
