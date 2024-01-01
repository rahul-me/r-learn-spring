package com.rahulchauhan.service;

import com.rahulchauhan.model.Invoice;
import com.rahulchauhan.model.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InvoiceService {

    private final UserService userService;

    private final JdbcTemplate jdbcTemplate;

    RowMapper<Invoice> invoiceRowMapper = (rs, no) -> {
        return Invoice.builder()
                .id(rs.getInt("id"))
                .userId(rs.getString("userid"))
                .pdfUrl(rs.getString("pdfurl"))
                .amount(rs.getInt("amount"))
                .build();
    };

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
    public InvoiceService(UserService userService, @Value("${cdn.url}") String cdnUrl, JdbcTemplate jdbcTemplate) {
        this.userService = userService;
        this.jdbcTemplate = jdbcTemplate;
    }

    List<Invoice> invoices = new CopyOnWriteArrayList<>();

    public List<Invoice> findAll() {

        List<Invoice> invoices = jdbcTemplate.query("select * from invoice", invoiceRowMapper);
        return invoices;
    }

    public Invoice findByUser(String userId) {
        return jdbcTemplate.queryForObject("select * from invoice where userid = ?", invoiceRowMapper , userId);
    }



    public Invoice create(String userId, Integer amount) {

        Invoice invoice = new Invoice();

        User user = userService.findById(userId);
        if (user == null) {
            throw new IllegalStateException();
        }

        int rowsAffected = jdbcTemplate.update("insert into invoice (pdfurl, userid, amount) values (?,?,?)", (ps) -> {
            ps.setString(1, "http://www.africau.edu/images/default/sample.pdf");
            ps.setString(2, userId);
            ps.setInt(3, amount);
        });

        if(rowsAffected > 0) {
            invoice = findByUser(userId);
        }


        // TODO real pdf creation and storing it on network server
//        var invoice = Invoice.builder()
//                .id((int)(Math.random() * 100))
//                .userId(userId)
//                .amount(amount)
//                .pdfUrl("http://www.africau.edu/images/default/sample.pdf")
//                .build();
//
//        invoices.add(invoice);
        return invoice;
    }

    public Invoice createAndGetGeneratedId(String userId, int amount) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update( con -> {
            PreparedStatement ps = con.prepareStatement("insert into invoice (pdfurl, userid, amount) values (?,?,?)", new String[] {"id"});
            ps.setString(1, "http://www.africau.edu/images/default/sample.pdf");
            ps.setString(2, userId);
            ps.setInt(3, amount);
            return ps;
        }, keyHolder);

        var invoice = Invoice.builder()
                .id(Objects.requireNonNull(keyHolder.getKey()).intValue())
                .userId(userId)
                .amount(amount)
                .pdfUrl("http://www.africau.edu/images/default/sample.pdf")
                .build();

        return invoice;
    }
}
