package com.rahulchauhan.web;

import com.rahulchauhan.model.Invoice;
import com.rahulchauhan.model.InvoiceRequest;
import com.rahulchauhan.service.InvoiceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * READ-7
 *
 * To reiterate from the last lesson, @Controller is a marker annotation in Spring that signals Spring
 * that your class contains methods that can return something HTTP related (HTML, JSON, XML) to the end-user or a browser.
 *
 * @ResponseBody tells Spring, that you want to write JSON or XML or plain text directly
 * to the HttpServletOutputstream, but without going through an HTML templating framework -
 * which Spring would assume by default if you didn’t annotate your method with @ResponseBody.
 * You will learn more about rendering HTML in the next module, so more on that later on.
 *
 * Also, you might be wondering what the difference is in annotating single methods or the whole class with @ResponseBody:
 *
 * If you put the annotation on the class, it applies to every public method inside that class.
 *
 * If you put the annotation on a method, it applies to only that method.
 */
@RestController
@RequestMapping("/v1/invoice")
public class InvoicesController {


    private InvoiceService invoiceService;

    public InvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    /**
     * READ-6
     *
     * @GetMapping("/") will make sure that this method is called by Spring MVC in response to HTTP GET requests
     *
     * While you added the spring-webmvc dependency to your project and created your first controller,
     * your Tomcat doesn’t know anything about that, yet. It only knows about your servlet.
     *
     * Creating and registering Spring’s DispatcherServlet
     * You do not need to create that DispatcherServlet from scratch. It comes bundled with Spring MVC. You only need to register it with Tomcat.
     *
     * What that servlet will do, is take the incoming HTTP request (think: http://localhost:8080) and forward it to the @Controller you just wrote.
     * And then take whatever your controller returns, and write it to the HttpServletResponse. This is why it’s called Dispatcher Servlet,
     * because its job is literally to dispatch requests and responses to and from @Controllers.
     * @return
     */
    @GetMapping
    public List<Invoice> index(){
        return invoiceService.findAll();
    }

    @PostMapping
    public Invoice create( @RequestBody @Valid InvoiceRequest invoiceRequest) {
        return invoiceService.create(invoiceRequest.getUserId(), invoiceRequest.getAmount());
    }

    @PostMapping("/params")
    public Invoice create(@RequestParam @NotBlank String userId, @RequestParam Integer amount) {
        return create(userId, amount);
    }

}
