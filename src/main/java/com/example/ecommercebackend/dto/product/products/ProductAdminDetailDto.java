package com.example.ecommercebackend.dto.product.products;

import com.example.ecommercebackend.dto.file.ImageDetailDto;
import com.example.ecommercebackend.dto.product.supplier.SupplierDetailDto;
import com.example.ecommercebackend.dto.product.tag.TagDetailDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class ProductAdminDetailDto {
    private int id;
    private String name;
    private String linkName;
    private String shortDescription;
    private String description;

    private BigDecimal buyingPrice;
    private BigDecimal salePrice;
    private BigDecimal comparePrice;

    private ImageDetailDto coverImage;
    private List<ImageDetailDto> productImage;
    private int quantity;

    private Set<SupplierDetailDto> supplier;
    private Set<TagDetailDto> tag;
    private Boolean isDeleted;
    private BigDecimal taxRate;

    public ProductAdminDetailDto(int id, String name, String linkName, String shortDescription, String description, BigDecimal buyingPrice, BigDecimal salePrice, BigDecimal comparePrice, ImageDetailDto coverImage, List<ImageDetailDto> productImage, int quantity, Set<SupplierDetailDto> supplier, Set<TagDetailDto> tag, Boolean isDeleted, BigDecimal taxRate) {
        this.id = id;
        this.name = name;
        this.linkName = linkName;
        this.shortDescription = shortDescription;
        this.description = description;
        this.buyingPrice = buyingPrice;
        this.salePrice = salePrice;
        this.comparePrice = comparePrice;
        this.coverImage = coverImage;
        this.productImage = productImage;
        this.quantity = quantity;
        this.supplier = supplier;
        this.tag = tag;
        this.isDeleted = isDeleted;
        this.taxRate = taxRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(BigDecimal buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getComparePrice() {
        return comparePrice;
    }

    public void setComparePrice(BigDecimal comparePrice) {
        this.comparePrice = comparePrice;
    }

    public ImageDetailDto getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(ImageDetailDto coverImage) {
        this.coverImage = coverImage;
    }

    public List<ImageDetailDto> getProductImage() {
        return productImage;
    }

    public void setProductImage(List<ImageDetailDto> productImage) {
        this.productImage = productImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Set<SupplierDetailDto> getSupplier() {
        return supplier;
    }

    public void setSupplier(Set<SupplierDetailDto> supplier) {
        this.supplier = supplier;
    }

    public Set<TagDetailDto> getTag() {
        return tag;
    }

    public void setTag(Set<TagDetailDto> tag) {
        this.tag = tag;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
