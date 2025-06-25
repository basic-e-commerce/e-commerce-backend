package com.example.ecommercebackend.dto.product.invoice;

public class CorporateInvoiceResponseDto {
    private String companyName;
    private String taxNumber;
    private String taxOffice;

    public CorporateInvoiceResponseDto(String companyName, String taxNumber, String taxOffice) {
        this.companyName = companyName;
        this.taxNumber = taxNumber;
        this.taxOffice = taxOffice;
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
