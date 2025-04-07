package com.example.ecommercebackend.entity.product.invoice;

import com.example.ecommercebackend.entity.payment.Payment;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "corporate_invoice")
public class CorporateInvoice extends Invoice {
    private String companyName;

    public CorporateInvoice(Payment payment, BigDecimal totalAmount, BigDecimal taxAmount, InvoiceType invoiceType, String companyName) {
        super(payment, totalAmount, taxAmount, invoiceType);
        this.companyName = companyName;
    }

    public CorporateInvoice() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
