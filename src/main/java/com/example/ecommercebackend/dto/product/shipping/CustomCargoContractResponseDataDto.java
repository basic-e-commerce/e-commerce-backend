package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CustomCargoContractResponseDataDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("providerCode")
    private String providerCode;

    @JsonProperty("username")
    private String username;

    @JsonProperty("name")
    private String name;

    @JsonProperty("isActive")
    private boolean isActive;

    @JsonProperty("parameters")
    private String parameters;

    @JsonProperty("version")
    private int version;

    @JsonProperty("isC2C")
    private boolean isC2C;

    @JsonProperty("integrationType")
    private String integrationType;

    @JsonProperty("labelFileType")
    private String labelFileType;

    @JsonProperty("sharable")
    private boolean sharable;

    @JsonProperty("isPublic")
    private boolean isPublic;

    @JsonProperty("isDynamicPrice")
    private boolean isDynamicPrice;

    @JsonProperty("priceUpdatedAt")
    private String priceUpdatedAt;

    public CustomCargoContractResponseDataDto(String id, String createdAt, String updatedAt, String providerCode, String username, String name, boolean isActive, String parameters, int version, boolean isC2C, String integrationType, String labelFileType, boolean sharable, boolean isPublic, boolean isDynamicPrice, String priceUpdatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.providerCode = providerCode;
        this.username = username;
        this.name = name;
        this.isActive = isActive;
        this.parameters = parameters;
        this.version = version;
        this.isC2C = isC2C;
        this.integrationType = integrationType;
        this.labelFileType = labelFileType;
        this.sharable = sharable;
        this.isPublic = isPublic;
        this.isDynamicPrice = isDynamicPrice;
        this.priceUpdatedAt = priceUpdatedAt;
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

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isC2C() {
        return isC2C;
    }

    public void setC2C(boolean c2C) {
        isC2C = c2C;
    }

    public String getIntegrationType() {
        return integrationType;
    }

    public void setIntegrationType(String integrationType) {
        this.integrationType = integrationType;
    }

    public String getLabelFileType() {
        return labelFileType;
    }

    public void setLabelFileType(String labelFileType) {
        this.labelFileType = labelFileType;
    }

    public boolean isSharable() {
        return sharable;
    }

    public void setSharable(boolean sharable) {
        this.sharable = sharable;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isDynamicPrice() {
        return isDynamicPrice;
    }

    public void setDynamicPrice(boolean dynamicPrice) {
        isDynamicPrice = dynamicPrice;
    }

    public String getPriceUpdatedAt() {
        return priceUpdatedAt;
    }

    public void setPriceUpdatedAt(String priceUpdatedAt) {
        this.priceUpdatedAt = priceUpdatedAt;
    }
}
