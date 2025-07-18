package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CargoOfferData {
    @JsonProperty("id")
    private String id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("test")
    private boolean test;

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
    private boolean useWeightOfItems;

    @JsonProperty("useDimensionsOfItems")
    private boolean useDimensionsOfItems;

    @JsonProperty("trackingStatus")
    private TrackingStatusDto trackingStatus;


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
    private OrderShippingDto order;

    @JsonProperty("senderAddressID")
    private String senderAddressID;

    @JsonProperty("senderAddress")
    private AddressReceiptDto senderAddress;

    @JsonProperty("recipientAddressID")
    private String recipientAddressID;

    @JsonProperty("recipientAddress")
    private AddressReceiptDto recipientAddress;

    @JsonProperty("returnAddressID")
    private String returnAddressID;

    @JsonProperty("createReturnLabel")
    private Boolean createReturnLabel;

    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("offers")
    private OffersDto offers;

    @JsonProperty("acceptedOffer")
    private String acceptedOffer;

    @JsonProperty("enableAutomation")
    private boolean enableAutomation;

    @JsonProperty("items")
    private List<ShippingItemsResponseDto> items;

    @JsonProperty("organizationShipmentID")
    private int organizationShipmentID;

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
    private boolean isRecipientSmsActivated;

    @JsonProperty("packageAcceptedAt")
    private String packageAcceptedAt;

    @JsonProperty("tenantId")
    private String tenantId;

    public CargoOfferData(String id, String createdAt, String updatedAt, boolean test, String amount, String currency, String amountLocal, String currencyLocal, String amountVat, String amountLocalVat, String amountTax, String amountLocalTax, String totalAmount, String totalAmountLocal, String amountOld, String amountLocalOld, String discountRate, String bonusBalance, String length, String width, String height, String desi, String oldDesi, String distanceUnit, String weight, String oldWeight, String massUnit, boolean useWeightOfItems, boolean useDimensionsOfItems, TrackingStatusDto trackingStatus, String labelFileType, boolean hidePackageContentOnTag, String shipmentDate, boolean invoiceGenerated, String refundInvoiceID, boolean productPaymentOnDelivery, String orderID, OrderShippingDto order, String senderAddressID, AddressReceiptDto senderAddress, String recipientAddressID, AddressReceiptDto recipientAddress, String returnAddressID, Boolean createReturnLabel, String statusCode, OffersDto offers, String acceptedOffer, boolean enableAutomation, List<ShippingItemsResponseDto> items, int organizationShipmentID, String providerBranchName, String providerInvoiceNo, String providerReceiptNo, String providerSerialNo, boolean hasError, String lastErrorMessage, String lastErrorCode, String cancelDate, boolean isReturned, boolean isReturn, boolean isTrackingOnly, boolean isRecipientSmsActivated, String packageAcceptedAt, String tenantId) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.test = test;
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
        this.senderAddressID = senderAddressID;
        this.senderAddress = senderAddress;
        this.recipientAddressID = recipientAddressID;
        this.recipientAddress = recipientAddress;
        this.returnAddressID = returnAddressID;
        this.createReturnLabel = createReturnLabel;
        this.statusCode = statusCode;
        this.offers = offers;
        this.acceptedOffer = acceptedOffer;
        this.enableAutomation = enableAutomation;
        this.items = items;
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

    public CargoOfferData() {
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

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
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

    public TrackingStatusDto getTrackingStatus() {
        return trackingStatus;
    }

    public void setTrackingStatus(TrackingStatusDto trackingStatus) {
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

    public OrderShippingDto getOrder() {
        return order;
    }

    public void setOrder(OrderShippingDto order) {
        this.order = order;
    }

    public String getSenderAddressID() {
        return senderAddressID;
    }

    public void setSenderAddressID(String senderAddressID) {
        this.senderAddressID = senderAddressID;
    }

    public AddressReceiptDto getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(AddressReceiptDto senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getRecipientAddressID() {
        return recipientAddressID;
    }

    public void setRecipientAddressID(String recipientAddressID) {
        this.recipientAddressID = recipientAddressID;
    }

    public AddressReceiptDto getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(AddressReceiptDto recipientAddress) {
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

    public OffersDto getOffers() {
        return offers;
    }

    public void setOffers(OffersDto offers) {
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

    public List<ShippingItemsResponseDto> getItems() {
        return items;
    }

    public void setItems(List<ShippingItemsResponseDto> items) {
        this.items = items;
    }

    public int getOrganizationShipmentID() {
        return organizationShipmentID;
    }

    public void setOrganizationShipmentID(int organizationShipmentID) {
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

    public boolean isRecipientSmsActivated() {
        return isRecipientSmsActivated;
    }

    public void setRecipientSmsActivated(boolean recipientSmsActivated) {
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

    public String getReturnAddressID() {
        return returnAddressID;
    }

    public void setReturnAddressID(String returnAddressID) {
        this.returnAddressID = returnAddressID;
    }
}
