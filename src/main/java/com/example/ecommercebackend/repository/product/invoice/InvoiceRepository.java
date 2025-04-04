package com.example.ecommercebackend.repository.product.invoice;

import com.example.ecommercebackend.entity.product.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {
}
