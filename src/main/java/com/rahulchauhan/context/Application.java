package com.rahulchauhan.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahulchauhan.service.InvoiceService;
import com.rahulchauhan.service.UserService;

public class Application {

    public static final UserService userService = new UserService();
    public static final InvoiceService invoiceService = new InvoiceService(userService);
    public static final ObjectMapper objectMapper = new ObjectMapper();
}
