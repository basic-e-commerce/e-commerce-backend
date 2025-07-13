package com.example.ecommercebackend.entity.product.invoice;

import com.example.ecommercebackend.entity.payment.Payment;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "corporate_invoice")
public class CorporateInvoice extends Invoice {
    private String companyName;
    private String taxNumber;
    private String taxOffice;

    public CorporateInvoice(Payment payment, BigDecimal totalAmount, BigDecimal taxAmount, InvoiceType invoiceType, String firstName, String lastName, String username, String countryName, String city,String cityCode,String district,String districtId, String addressLine1, String postalCode, String phoneNo, String companyName, String taxNumber, String taxOffice) {
        super(payment, totalAmount, taxAmount, invoiceType, firstName, lastName, username, countryName, city,cityCode, district,districtId, addressLine1, postalCode, phoneNo);
        this.companyName = companyName;
        this.taxNumber = taxNumber;
        this.taxOffice = taxOffice;
    }

    public CorporateInvoice() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getTaxOffice() {
        return taxOffice;
    }

    public void setTaxOffice(String taxOffice) {
        this.taxOffice = taxOffice;
    }
}
