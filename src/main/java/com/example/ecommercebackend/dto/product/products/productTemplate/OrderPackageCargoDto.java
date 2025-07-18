package com.example.ecommercebackend.dto.product.products.productTemplate;

public class OrderPackageCargoDto {
    private double length;  // cm
    private double width;
    private double height;
    private double weight;  // kg
    private double desi;

    public OrderPackageCargoDto(double length, double width, double height, double weight) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.desi = length*width*height;
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

    public double getDesi() {
        return desi;
    }

    public void setDesi(double desi) {
        this.desi = desi;
    }
}
