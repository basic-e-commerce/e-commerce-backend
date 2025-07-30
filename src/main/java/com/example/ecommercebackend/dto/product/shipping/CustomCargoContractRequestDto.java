package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CustomCargoContractRequestDto {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("name")
    private String name;

    @JsonProperty("providerCode")
    private String providerCode;

    @JsonProperty("version")
    private int version;

    @JsonProperty("isActive")
    private boolean isActive;

    @JsonProperty("parameters")
    private Map<String, Object> parameters;

    @JsonProperty("isPublic")
    private boolean isPublic;

    @JsonProperty("sharable")
    private boolean sharable;

    @JsonProperty("isDynamicPrice")
    private boolean isDynamicPrice;

    public CustomCargoContractRequestDto(String username, String password, String name, String providerCode, int version, boolean isActive, Map<String, Object> parameters, boolean isPublic, boolean sharable, boolean isDynamicPrice) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.providerCode = providerCode;
        this.version = version;
        this.isActive = isActive;
        this.parameters = parameters;
        this.isPublic = isPublic;
        this.sharable = sharable;
        this.isDynamicPrice = isDynamicPrice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isSharable() {
        return sharable;
    }

    public void setSharable(boolean sharable) {
        this.sharable = sharable;
    }

    public boolean isDynamicPrice() {
        return isDynamicPrice;
    }

    public void setDynamicPrice(boolean dynamicPrice) {
        isDynamicPrice = dynamicPrice;
    }
}
