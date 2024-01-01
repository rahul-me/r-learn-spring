package com.rahulchauhan.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahulchauhan.context.ApplicationConfiguration;
import com.rahulchauhan.model.Invoice;
import com.rahulchauhan.service.InvoiceService;
import com.rahulchauhan.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

@Deprecated
public class MyFancyPdfInvoicesServlet extends HttpServlet {

    UserService userService;
    InvoiceService invoiceService;

    ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        userService = context.getBean(UserService.class);
        invoiceService = context.getBean(InvoiceService.class);
        objectMapper = context.getBean(ObjectMapper.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().equalsIgnoreCase("/")) {
        response.setContentType("text/html; charset=UTF-8");
        String content = """
                <html>
                <body>
                <h1>Hello World</h1>
                <p>This is my very first, embedded Tomcat, HTML Page!</p>
                </body>
                </html>
                """;
        response.getWriter().print(content);

        } else if (request.getRequestURI().equalsIgnoreCase("/invoices")) {
            response.setContentType("application/json; charset=UTF-8");
            List<Invoice> invoices = invoiceService.findAll();  //
            response.getWriter().print(objectMapper.writeValueAsString(invoices));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getRequestURI().equalsIgnoreCase("/invoices")) {

            String userId = request.getParameter("user_id");
            Integer amount = Integer.valueOf(request.getParameter("amount"));

            Invoice invoice = invoiceService.create(userId, amount);

            response.setContentType("application/json; charset=UTF-8");
            String json = objectMapper.writeValueAsString(invoice);
            response.getWriter().print(json);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}