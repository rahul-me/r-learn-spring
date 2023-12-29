package com.rahulchauhan.service;

import com.rahulchauhan.model.Invoice;
import com.rahulchauhan.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InvoiceService {

    private final UserService userService;

    @PostConstruct
    public void init() {
        System.out.println("Fetching PDF Template from S3...");
        // TODO download from s3 and save locally
    }

    /**
     * Read-1
     * Constructor Injection - mostly preferred
     * There are other injection methods
     * 1. Field Injection
     * 2. Setter Injection

     * Spring injects fields after calling the constructor.
     *
     * Read-4
     * @Value
     * Similarly to @Autowired you can inject properties that exist in any of your specified @PropertySources into any Spring bean,
     * with the @Value annotation. Make sure to use the ${} syntax when specifying the key
     *
     * @param userService
     */
    public InvoiceService(UserService userService, @Value("${cdn.url}") String cdnUrl) {
        this.userService = userService;
    }

    List<Invoice> invoices = new CopyOnWriteArrayList<>();

    public List<Invoice> findAll() {
        return invoices;
    }

    public Invoice create(String userId, Integer amount) {

        User user = userService.findById(userId);
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
