package com.rahulchauhan.service;

import com.rahulchauhan.context.Application;
import com.rahulchauhan.model.Invoice;
import com.rahulchauhan.model.User;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class InvoiceService {

    private final UserService userService;

    public InvoiceService(UserService userService) {
        this.userService = userService;
    }

    List<Invoice> invoices = new CopyOnWriteArrayList<>();

    public List<Invoice> findAll() {
        return invoices;
    }

    public Invoice create(String userId, Integer amount) {

        User user = Application.userService.findById(userId);
        if (user == null) {
            throw new IllegalStateException();
        }

        // TODO real pdf creation and storing it on network server
        var invoice = Invoice.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .amount(amount)
                .pdfUrl("http://www.africau.edu/images/default/sample.pdf")
                .build();

        invoices.add(invoice);
        return invoice;
    }
}
