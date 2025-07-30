package com.example.ecommercebackend.entity.merchant;

import com.example.ecommercebackend.entity.product.order.OrderPackage;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "custom_cargo_contract")
public class CustomCargoContract {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "custom_cargo_contract_seq")
    @SequenceGenerator(name = "custom_cargo_contract_seq", sequenceName = "custom_cargo_contract_seq", allocationSize = 1)
    private int id;

    private String cargoContractId; // id: "1498ccac-f318-4f55-9546-53721d858c7f"

    private String username;

    private String name;

    @Enumerated(EnumType.STRING)
    private OrderPackage.CargoCompany cargoCompany; // providerCode: "SURAT"

    private boolean isActive;

    private String parameters; // JSON olarak tutulacaksa String, ya da @Convert ile Map

    private int version;

    private boolean isC2C;

    private String integrationType;

    private String labelFileType;

    private boolean sharable;

    private boolean isPublic;

    private boolean isDynamicPrice;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant priceUpdatedAt;

    public CustomCargoContract(String cargoContractId, String username, String name, OrderPackage.CargoCompany cargoCompany, boolean isActive, String parameters, int version, boolean isC2C, String integrationType, String labelFileType, boolean sharable, boolean isPublic, boolean isDynamicPrice, Instant createdAt, Instant updatedAt, Instant priceUpdatedAt) {
        this.cargoContractId = cargoContractId;
        this.username = username;
        this.name = name;
        this.cargoCompany = cargoCompany;
        this.isActive = isActive;
        this.parameters = parameters;
        this.version = version;
        this.isC2C = isC2C;
        this.integrationType = integrationType;
        this.labelFileType = labelFileType;
        this.sharable = sharable;
        this.isPublic = isPublic;
        this.isDynamicPrice = isDynamicPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.priceUpdatedAt = priceUpdatedAt;
    }

    public CustomCargoContract() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCargoContractId() {
        return cargoContractId;
    }

    public void setCargoContractId(String cargoContractId) {
        this.cargoContractId = cargoContractId;
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

    public OrderPackage.CargoCompany getCargoCompany() {
        return cargoCompany;
    }

    public void setCargoCompany(OrderPackage.CargoCompany cargoCompany) {
        this.cargoCompany = cargoCompany;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getPriceUpdatedAt() {
        return priceUpdatedAt;
    }

    public void setPriceUpdatedAt(Instant priceUpdatedAt) {
        this.priceUpdatedAt = priceUpdatedAt;
    }
}
