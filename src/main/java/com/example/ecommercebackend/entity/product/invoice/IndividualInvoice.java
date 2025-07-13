package com.example.ecommercebackend.entity.product.invoice;

import com.example.ecommercebackend.entity.payment.Payment;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "individual_invoice")
public class IndividualInvoice extends Invoice{

    public IndividualInvoice(Payment payment, BigDecimal totalAmount, BigDecimal taxAmount, InvoiceType invoiceType, String firstName, String lastName, String username, String countryName, String city,String cityCode,String district,String districtId, String addressLine1, String postalCode, String phoneNo) {
        super(payment, totalAmount, taxAmount, invoiceType, firstName, lastName, username, countryName, city,cityCode,district,districtId, addressLine1, postalCode, phoneNo);
    }

    public IndividualInvoice() {

    }
}
