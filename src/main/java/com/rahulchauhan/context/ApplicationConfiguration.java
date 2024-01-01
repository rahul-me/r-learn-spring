package com.rahulchauhan.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.rahulchauhan.ApplicationLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

/**
 * READ-2
 * @Configuration
 * Every Spring application consists of central class, though Spring calls it ApplicationContext
 * Spring needs a configuration class in order to construct an ApplicationContext
 *
 * READ-3
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
/**
 * READ-8
 * <!doctype html>
 * <html lang="en">
 * <head><title>HTTP Status 406 – Not Acceptable</title>
 * </head>
 * <body><h1>HTTP Status 406 – Not Acceptable</h1>
 * <hr class="line"/>
 * <p><b>Type</b> Status Report</p>
 * <p><b>Description</b> The target resource does not have a current representation that would be acceptable to the user
 *     agent, according to the proactive negotiation header fields received in the request, and the server is unwilling to
 *     supply a default representation.</p>
 * <hr class="line"/>
 * <h3>Apache Tomcat/10.1.8</h3></body>
 * </html>
 *
 * The most important line is this:
 * <p><b>Description</b>  The target resource does not have a current representation that would be acceptable to the user</p>
 * Remember, your InvoiceService returns such a CopyOnWriteArrayList.

 * Spring does NOT know what to do with that list, by default. It says: I don’t know of any converters that could do something with that list, before sending it back to the user.

 * You would love to turn that list into JSON. So, a JSON converter would be something. How do you make Spring use such a JSON converter?

 * Adding @EnableWebMvc
 * For that, you need a bit of annotation magic, more specifically one annotation called @EnableWebMvc.

 * Open up your SpringConfiguration and add the @EnableWebMvc annotation to it.
 *
 *
 * MIMP
 * This annotation makes sure that Spring Web MVC gets initialized with a sane default configuration.
 *
 * That default configuration, among other things,
 * automatically registers a JSON converter with Spring MVC, as long as you have the jackson dependency on your classpath.
 */
@EnableWebMvc


/**
 * You need to annotate your Spring configuration with the @EnableTransactionManagement annotation.
 * It allows you to use the @Transactional annotation, to declare transactions programmatically.
 * (You do not need to do this when using Spring Boot, as it will apply this annotation automatically for you.)
 */
@EnableTransactionManagement
public class ApplicationConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public DataSource dataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/firstone");
        dataSource.setUser("root");
        dataSource.setPassword("macroot10");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Reference: https://www.marcobehler.com/courses/spring-professional/spring-database/spring-database-transactions
     *
     * @param dataSource
     * @return
     */
    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
