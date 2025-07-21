package com.example.ecommercebackend.entity.product.order;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "order_package")
public class OrderPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_package_seq")
    @SequenceGenerator(name = "order_package_seq", sequenceName = "order_package_seq", allocationSize = 1)
    private int id;

    @ManyToMany
    @JoinTable(
            name = "order_package_order_items",
            joinColumns = @JoinColumn(name = "order_package_id"),
            inverseJoinColumns = @JoinColumn(name = "order_item_id")
    )
    private Set<OrderItem> orderItems;

    private String packageName;
    private double length;  // cm
    private double width;
    private double height;
    private double weight;  // kg

    private String shipmentId;
    private String responsiveLabelURL;

    @Column(name = "status_code",nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusCode statusCode = StatusCode.PRE_TRANSIT;

    @Column(name = "cargo_company")
    @Enumerated(EnumType.STRING)
    private CargoCompany cargoCompany;

    @Column(name = "cargo_status")
    @Enumerated(EnumType.STRING)
    private CargoStatus cargoStatus;

    @Column(name = "cargo_id")
    private String cargoId;
    private String barcode;
    private Boolean productPaymentOnDelivery;
    private Boolean isCanceled;
    private Boolean isRefund;



    public OrderPackage(Set<OrderItem> orderItems, String packageName, double length, double width, double height, double weight, String shipmentId, String responsiveLabelURL, CargoCompany cargoCompany, CargoStatus cargoStatus, String cargoId, String barcode, Boolean productPaymentOnDelivery, Boolean isCanceled, Boolean isRefund) {
        this.orderItems = orderItems;
        this.packageName = packageName;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.shipmentId = shipmentId;
        this.responsiveLabelURL = responsiveLabelURL;
        this.cargoCompany = cargoCompany;
        this.cargoStatus = cargoStatus;
        this.cargoId = cargoId;
        this.barcode = barcode;
        this.productPaymentOnDelivery = productPaymentOnDelivery;
        this.isCanceled = isCanceled;
        this.isRefund = isRefund;
    }

    public OrderPackage() {
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getResponsiveLabelURL() {
        return responsiveLabelURL;
    }

    public void setResponsiveLabelURL(String responsiveLabelURL) {
        this.responsiveLabelURL = responsiveLabelURL;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Boolean getProductPaymentOnDelivery() {
        return productPaymentOnDelivery;
    }

    public void setProductPaymentOnDelivery(Boolean productPaymentOnDelivery) {
        this.productPaymentOnDelivery = productPaymentOnDelivery;
    }

    public Boolean getCanceled() {
        return isCanceled;
    }

    public void setCanceled(Boolean canceled) {
        isCanceled = canceled;
    }

    public Boolean getRefund() {
        return isRefund;
    }

    public void setRefund(Boolean refund) {
        isRefund = refund;
    }

    public enum StatusCode{
        PRE_TRANSIT,
        TRANSIT,
        DELIVERED,
        FAILURE,
        RETURNED,
        UNKNOWN
    }

    public enum CargoCompany{
        SURAT_STANDART,
        YURTICI_STANDART,
        PTT_STANDART,
        PTT_KAPIDA_ODEME,
        SENDEO_STANDART,
        MNG_STANDART,
        HEPSIJET_STANDART,
        KOLAYGELSIN_STANDART,
        PAKETTAXI_STANDART,
        ARAS_STANDART,
        GELIVER_STANDART
    }

    public enum CargoStatus{
        INFORMATION_RECEIVED("Kargo sistemde oluşturuldu"),
        PACKAGE_ACCEPTED("Kargo, Gönderici şube tarafından teslim alındı"),
        PACKAGE_DEPARTED("Kargo, aktarma merkezinden ayrıldı"),
        PACKAGE_PROCESSING("Kargo aktarma merkezinde"),
        DELIVERY_SCHEDULED("Kargo alıcı şubede"),
        OUT_OF_DELIVERY("Kargo Dağıtımda"),
        PACKAGE_DAMAGE("Paket Hasarlı"),
        PACKAGE_FORWARDED_TO_ANOTHER_CARRIER("Kargo aracı firmaya verildi"),
        DELIVERY_RESCHEDULED("Kargo dağıtım zamanı değişti"),
        DELIVERED("Kargo alıcıya teslim edildi"),
        PACKAGE_LOST("Paket Kayboldu"),
        PACKAGE_UNDELIVERABLE("Kargo dağıtılamıyor."),
        RETURN_TO_SENDER("Kargo iade edildi"),
        OTHER("Bilinmeyen Durum");

        CargoStatus(String s) {
        }
        public String getValue() {
            return name();
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public CargoCompany getCargoCompany() {
        return cargoCompany;
    }

    public void setCargoCompany(CargoCompany cargoCompany) {
        this.cargoCompany = cargoCompany;
    }

    public CargoStatus getCargoStatus() {
        return cargoStatus;
    }

    public void setCargoStatus(CargoStatus cargoStatus) {
        this.cargoStatus = cargoStatus;
    }

    public String getCargoId() {
        return cargoId;
    }

    public void setCargoId(String cargoId) {
        this.cargoId = cargoId;
    }
}
