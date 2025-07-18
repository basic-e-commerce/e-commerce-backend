package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class CargoDetailDataDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("test")
    private Boolean test;

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
    private TrackingStatusDto trackingStatus;

    @JsonProperty("trackingNumber")
    private String trackingNumber;

    @JsonProperty("barcode")
    private String barcode;

    @JsonProperty("trackingUrl")
    private String trackingUrl;

    @JsonProperty("labelFileType")
    private String labelFileType;

    @JsonProperty("labelURL")
    private String labelURL;

    @JsonProperty("responsiveLabelURL")
    private String responsiveLabelURL;

    @JsonProperty("hidePackageContentOnTag")
    private Boolean hidePackageContentOnTag;

    @JsonProperty("shipmentDate")
    private String shipmentDate;

    @JsonProperty("invoiceGenerated")
    private Boolean invoiceGenerated;

    @JsonProperty("invoiceID")
    private String invoiceID;

    @JsonProperty("refundInvoiceID")
    private String refundInvoiceID;

    @JsonProperty("productPaymentOnDelivery")
    private Boolean productPaymentOnDelivery;

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
    private AddressReceiptCargoDto recipientAddress;

    @JsonProperty("createReturnLabel")
    private String createReturnLabel;

    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("offers")
    private OffersDto offer;

    @JsonProperty("acceptedOfferID")
    private String acceptedOfferID;

    @JsonProperty("acceptedOffer")
    private OfferDto acceptedOffer;

    @JsonProperty("providerCode")
    private String providerCode;

    @JsonProperty("providerServiceCode")
    private String providerServiceCode;

    @JsonProperty("enableAutomation")
    private Boolean enableAutomation;

    @JsonProperty("items")
    private List<ShippingItemsResponseDto> items;

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

    public CargoDetailDataDto(String id, String createdAt, String updatedAt, Boolean test, String amount, String currency, String amountLocal, String amountVat, String amountLocalVat, String amountTax, String amountLocalTax, String totalAmount, String totalAmountLocal, String amountOld, String amountLocalOld, String discountRate, String bonusBalance, String length, String width, String height, String desi, String oldDesi, String distanceUnit, String weight, String oldWeight, String massUnit, Boolean useWeightOfItems, Boolean useDimensionsOfItems, TrackingStatusDto trackingStatus, String trackingNumber, String barcode, String trackingUrl, String labelFileType, String labelURL, String responsiveLabelURL, Boolean hidePackageContentOnTag, String shipmentDate, Boolean invoiceGenerated, String invoiceID, String refundInvoiceID, Boolean productPaymentOnDelivery, String orderID, OrderShippingDto order, String senderAddressID, AddressReceiptDto senderAddress, String recipientAddressID, AddressReceiptCargoDto recipientAddress, String createReturnLabel, String statusCode, OffersDto offer, String acceptedOfferID, OfferDto acceptedOffer, String providerCode, String providerServiceCode, Boolean enableAutomation, List<ShippingItemsResponseDto> items, Integer organizationShipmentID, String providerBranchName, String providerInvoiceNo, String providerReceiptNo, String providerSerialNo, Boolean hasError, String lastErrorMessage, String lastErrorCode, String cancelDate, Boolean isReturned, Boolean isReturn, Boolean isTrackingOnly, Boolean isRecipientSmsActivated, String packageAcceptedAt, String tenantId) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.test = test;
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
        this.trackingNumber = trackingNumber;
        this.barcode = barcode;
        this.trackingUrl = trackingUrl;
        this.labelFileType = labelFileType;
        this.labelURL = labelURL;
        this.responsiveLabelURL = responsiveLabelURL;
        this.hidePackageContentOnTag = hidePackageContentOnTag;
        this.shipmentDate = shipmentDate;
        this.invoiceGenerated = invoiceGenerated;
        this.invoiceID = invoiceID;
        this.refundInvoiceID = refundInvoiceID;
        this.productPaymentOnDelivery = productPaymentOnDelivery;
        this.orderID = orderID;
        this.order = order;
        this.senderAddressID = senderAddressID;
        this.senderAddress = senderAddress;
        this.recipientAddressID = recipientAddressID;
        this.recipientAddress = recipientAddress;
        this.createReturnLabel = createReturnLabel;
        this.statusCode = statusCode;
        this.offer = offer;
        this.acceptedOfferID = acceptedOfferID;
        this.acceptedOffer = acceptedOffer;
        this.providerCode = providerCode;
        this.providerServiceCode = providerServiceCode;
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

    public CargoDetailDataDto() {
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

    public Boolean getTest() {
        return test;
    }

    public void setTest(Boolean test) {
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

    public TrackingStatusDto getTrackingStatus() {
        return trackingStatus;
    }

    public void setTrackingStatus(TrackingStatusDto trackingStatus) {
        this.trackingStatus = trackingStatus;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public String getLabelFileType() {
        return labelFileType;
    }

    public void setLabelFileType(String labelFileType) {
        this.labelFileType = labelFileType;
    }

    public String getLabelURL() {
        return labelURL;
    }

    public void setLabelURL(String labelURL) {
        this.labelURL = labelURL;
    }

    public String getResponsiveLabelURL() {
        return responsiveLabelURL;
    }

    public void setResponsiveLabelURL(String responsiveLabelURL) {
        this.responsiveLabelURL = responsiveLabelURL;
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

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
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

    public AddressReceiptCargoDto getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(AddressReceiptCargoDto recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public String getCreateReturnLabel() {
        return createReturnLabel;
    }

    public void setCreateReturnLabel(String createReturnLabel) {
        this.createReturnLabel = createReturnLabel;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public OffersDto getOffer() {
        return offer;
    }

    public void setOffer(OffersDto offer) {
        this.offer = offer;
    }

    public String getAcceptedOfferID() {
        return acceptedOfferID;
    }

    public void setAcceptedOfferID(String acceptedOfferID) {
        this.acceptedOfferID = acceptedOfferID;
    }

    public OfferDto getAcceptedOffer() {
        return acceptedOffer;
    }

    public void setAcceptedOffer(OfferDto acceptedOffer) {
        this.acceptedOffer = acceptedOffer;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getProviderServiceCode() {
        return providerServiceCode;
    }

    public void setProviderServiceCode(String providerServiceCode) {
        this.providerServiceCode = providerServiceCode;
    }

    public Boolean getEnableAutomation() {
        return enableAutomation;
    }

    public void setEnableAutomation(Boolean enableAutomation) {
        this.enableAutomation = enableAutomation;
    }

    public List<ShippingItemsResponseDto> getItems() {
        return items;
    }

    public void setItems(List<ShippingItemsResponseDto> items) {
        this.items = items;
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
