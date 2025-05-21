package com.example.ecommercebackend.service.invoice;

import com.example.ecommercebackend.entity.product.invoice.Invoice;
import com.example.ecommercebackend.repository.product.invoice.InvoiceRepository;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
}
