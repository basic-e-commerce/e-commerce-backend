package com.example.ecommercebackend.dto.product.order;

import com.example.ecommercebackend.anotation.NotNullField;
import com.example.ecommercebackend.dto.product.invoice.CorporateInvoiceCreateDto;
import com.example.ecommercebackend.dto.user.address.AddressOrderCreateDto;

import java.util.List;

public class OrderCreateDto {

    @NotNullField
    private AddressOrderCreateDto address;
    @NotNullField
    private Boolean diffAddress;
    private AddressOrderCreateDto invoiceAddress;
    @NotNullField
    private String invoiceType;
    private CorporateInvoiceCreateDto corporateInvoice;
    @NotNullField
    private List<OrderItemCreateDto> orderItemCreateDtos;

    public OrderCreateDto(AddressOrderCreateDto address, Boolean diffAddress, AddressOrderCreateDto invoiceAddress, String invoiceType, CorporateInvoiceCreateDto corporateInvoice, List<OrderItemCreateDto> orderItemCreateDtos) {
        this.address = address;
        this.diffAddress = diffAddress;
        this.invoiceAddress = invoiceAddress;
        this.invoiceType = invoiceType;
        this.corporateInvoice = corporateInvoice;
        this.orderItemCreateDtos = orderItemCreateDtos;
    }

    public AddressOrderCreateDto getAddress() {
        return address;
    }

    public void setAddress(AddressOrderCreateDto address) {
        this.address = address;
    }

    public Boolean getDiffAddress() {
        return diffAddress;
    }

    public void setDiffAddress(Boolean diffAddress) {
        this.diffAddress = diffAddress;
    }

    public AddressOrderCreateDto getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(AddressOrderCreateDto invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public CorporateInvoiceCreateDto getCorporateInvoice() {
        return corporateInvoice;
    }

    public void setCorporateInvoice(CorporateInvoiceCreateDto corporateInvoice) {
        this.corporateInvoice = corporateInvoice;
    }

    public List<OrderItemCreateDto> getOrderItemCreateDtos() {
        return orderItemCreateDtos;
    }

    public void setOrderItemCreateDtos(List<OrderItemCreateDto> orderItemCreateDtos) {
        this.orderItemCreateDtos = orderItemCreateDtos;
    }
}
