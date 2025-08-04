package com.example.ecommercebackend.entity.product.order;

import com.example.ecommercebackend.entity.product.products.ProductTemplate;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "order_package")
public class OrderPackage {

    private Boolean isManuel;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_package_seq")
    @SequenceGenerator(name = "order_package_seq", sequenceName = "order_package_seq", allocationSize = 1)
    private int id;

    @ManyToMany
    @JoinTable(
            name = "order_package_order_items",
            joinColumns = @JoinColumn(name = "order_package_id"),
            inverseJoinColumns = @JoinColumn(name = "order_items_id") // dikkat: s
    )
    private Set<OrderItem> orderItems;

    private String packageName;
    private double length;  // cm
    private double width;
    private double height;
    private double weight;  // kg
    private ProductTemplate.DistanceUnit distanceUnit;
    private ProductTemplate.MassUnit massUnit;


    private String shipmentId;
    private String responsiveLabelURL;

    private BigDecimal orderPackagePrice;

    @Column(name = "order_package_status_code",nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderPackageStatusCode orderPackageStatusCode = OrderPackageStatusCode.PRE_TRANSIT;

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
    private String location;


    private Instant createAt;
    private Instant updateAt;


    public OrderPackage(Boolean isManuel, Set<OrderItem> orderItems, String packageName, double length, double width, double height, double weight, ProductTemplate.DistanceUnit distanceUnit, ProductTemplate.MassUnit massUnit, String shipmentId, String responsiveLabelURL, BigDecimal orderPackagePrice, CargoCompany cargoCompany, CargoStatus cargoStatus, String cargoId, String barcode, Boolean productPaymentOnDelivery, Boolean isCanceled, Boolean isRefund, String location) {
        this.isManuel = isManuel;
        this.orderItems = orderItems;
        this.packageName = packageName;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.distanceUnit = distanceUnit;
        this.massUnit = massUnit;
        this.shipmentId = shipmentId;
        this.responsiveLabelURL = responsiveLabelURL;
        this.orderPackagePrice = orderPackagePrice;
        this.cargoCompany = cargoCompany;
        this.cargoStatus = cargoStatus;
        this.cargoId = cargoId;
        this.barcode = barcode;
        this.productPaymentOnDelivery = productPaymentOnDelivery;
        this.isCanceled = isCanceled;
        this.isRefund = isRefund;
        this.location = location;

    }

    public OrderPackage() {
    }

    @PrePersist
    public void prePersist() {
        this.createAt = Instant.now();
        this.updateAt = Instant.now();
    }
    @PreUpdate
    public void preUpdate() {
        this.updateAt = Instant.now();
    }

    public OrderPackageStatusCode getOrderPackageStatusCode() {
        return orderPackageStatusCode;
    }

    public void setOrderPackageStatusCode(OrderPackageStatusCode orderPackageStatusCode) {
        this.orderPackageStatusCode = orderPackageStatusCode;
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

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getOrderPackagePrice() {
        return orderPackagePrice;
    }

    public void setOrderPackagePrice(BigDecimal orderPackagePrice) {
        this.orderPackagePrice = orderPackagePrice;
    }

    public Boolean getManuel() {
        return isManuel;
    }

    public void setManuel(Boolean manuel) {
        isManuel = manuel;
    }

    public enum OrderPackageStatusCode{
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
        information_received("Kargo sistemde oluşturuldu"),
        package_accepted("Kargo, Gönderici şube tarafından teslim alındı"),
        package_departed("Kargo, aktarma merkezinden ayrıldı"),
        package_processing("Kargo aktarma merkezinde"),
        delivery_scheduled("Kargo alıcı şubede"),
        out_of_delivery("Kargo Dağıtımda"),
        package_damage("Paket Hasarlı"),
        package_forwarded_to_another_carrier("Kargo aracı firmaya verildi"),
        delivery_rescheduled("Kargo dağıtım zamanı değişti"),
        delivered("Kargo alıcıya teslim edildi"),
        package_lost("Paket Kayboldu"),
        package_undeliverable("Kargo dağıtılamıyor."),
        return_to_sender("Kargo iade edildi"),
        cancel("Kargo iptal edildi"),
        other("Bilinmeyen Durum");
        private String value;

        CargoStatus(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
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

    public ProductTemplate.DistanceUnit getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(ProductTemplate.DistanceUnit distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public ProductTemplate.MassUnit getMassUnit() {
        return massUnit;
    }

    public void setMassUnit(ProductTemplate.MassUnit massUnit) {
        this.massUnit = massUnit;
    }
}
