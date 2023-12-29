package com.rahulchauhan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyFirstSpringController {
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
    @GetMapping("/")
    @ResponseBody
    public String index(){
        return "hello";
    }
}
