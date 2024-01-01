package com.rahulchauhan;

import com.rahulchauhan.context.ApplicationConfiguration;
import jakarta.servlet.ServletContext;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationLauncher {
    public static void main(String[] args) throws LifecycleException {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context tomcatCtx = tomcat.addContext("", null);

        WebApplicationContext appCtx = createApplicationContext(tomcatCtx.getServletContext());
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appCtx);
        Wrapper servlet = Tomcat.addServlet(tomcatCtx, "dispatcherServlet", dispatcherServlet);

        /**
         * Here we have commented below line and added these lines
         * WebApplicationContext appCtx = createApplicationContext(tomcatCtx.getServletContext());
         * DispatcherServlet dispatcherServlet = new DispatcherServlet(appCtx);
         * Wrapper servlet = Tomcat.addServlet(tomcatCtx, "dispatcherServlet", dispatcherServlet);
         *
         * That means we removed our servlet file, MyFancyPdfInvoicesServlet and added spring servlet
         *
         * While you added the spring-webmvc dependency to your project and created your first controller,
         * your Tomcat doesn’t know anything about that, yet. It only knows about your servlet.
         *
         * This means you need to replace your servlet with Spring MVC’s own servlet, called DispatcherServlet.
         *
         * Creating and registering Spring’s DispatcherServlet
         * You do not need to create that DispatcherServlet from scratch. It comes bundled with Spring MVC. You only need to register it with Tomcat.
         *
         * What that servlet will do, is take the incoming HTTP request (think: http://localhost:8080) and
         * forward it to the @Controller you just wrote. And then take whatever your controller returns,
         * and write it to the HttpServletResponse. This is why it’s called Dispatcher Servlet,
         * because its job is literally to dispatch requests and responses to and from @Controllers.
         */
//        Wrapper servlet = Tomcat.addServlet(ctx, "myFirstServlet", new MyFancyPdfInvoicesServlet());
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/*");

        tomcat.start();
    }

    /**
     * READ-7
     *
     * Below Method has been added to create ApplicationContext related to Web
     *
     * @param servletContext
     * @return
     */
    public static WebApplicationContext createApplicationContext(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(ApplicationConfiguration.class);
        ctx.setServletContext(servletContext);
        ctx.refresh();
        ctx.registerShutdownHook();
        return ctx;
    }

}
