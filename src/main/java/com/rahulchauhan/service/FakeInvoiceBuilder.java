package com.rahulchauhan.service;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class FakeInvoiceBuilder {
    private InvoiceService invoiceService;
    public FakeInvoiceBuilder(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostConstruct
    public void fakeInvoices() {
        System.out.println("Faking invoices...");
        invoiceService.create("user-id-1", 20);
        invoiceService.create("user-id-2", 100);
    }
}
