package com.rahulchauhan.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahulchauhan.ApplicationLauncher;
import com.rahulchauhan.model.User;
import com.rahulchauhan.service.InvoiceService;
import com.rahulchauhan.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Read-2
 * @Configuration
 * Every Spring application consists of central class, though Spring calls it ApplicationContext
 * Spring needs a configuration class in order to construct an ApplicationContext
 *
 * Read-3
 * @PropertySource
 * With the help of the @PropertySource annotation,
 * Spring will try and read in properties files for you.
 * You can put in any Spring-Resources specific string here, like file:/, classpath:/ or even https:/.
 * For more information on the syntax, check out this link.
 * https://www.marcobehler.com/guides/spring-framework#spring-resources
 */

@PropertySource("classpath:/application.properties")
@Configuration
@ComponentScan(basePackageClasses = ApplicationLauncher.class)
public class PdfinvoiceAppConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
