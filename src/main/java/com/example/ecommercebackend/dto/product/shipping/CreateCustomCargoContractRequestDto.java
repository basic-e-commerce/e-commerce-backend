package com.example.ecommercebackend.dto.product.shipping;

import com.example.ecommercebackend.entity.product.order.OrderPackage;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CreateCustomCargoContractRequestDto {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
    private OrderPackage.CargoCompany cargoCompany;

    @JsonProperty("parameters")
    private Map<String, Object> parameters;

    private Boolean isActive;

    private boolean isPublic;

    private boolean sharable;

    private boolean isDynamicPrice;

    public CreateCustomCargoContractRequestDto(String username, String password, OrderPackage.CargoCompany cargoCompany, Map<String, Object> parameters, Boolean isActive, boolean isPublic, boolean sharable, boolean isDynamicPrice) {
        this.username = username;
        this.password = password;
        this.cargoCompany = cargoCompany;
        this.parameters = parameters;
        this.isActive = isActive;
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

    public OrderPackage.CargoCompany getCargoCompany() {
        return cargoCompany;
    }

    public void setCargoCompany(OrderPackage.CargoCompany cargoCompany) {
        this.cargoCompany = cargoCompany;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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
