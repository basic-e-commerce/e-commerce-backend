package com.example.ecommercebackend.entity.product.invoice;

import com.example.ecommercebackend.entity.payment.Payment;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "individual_invoice")
public class IndividualInvoice extends Invoice{
    public IndividualInvoice(Payment payment, BigDecimal totalAmount, BigDecimal taxAmount) {
        super(payment, totalAmount, taxAmount, InvoiceType.INDIVIDUAL);
    }
}
