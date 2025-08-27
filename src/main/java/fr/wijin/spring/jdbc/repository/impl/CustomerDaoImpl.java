package fr.wijin.spring.jdbc.repository.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import fr.wijin.spring.jdbc.model.Customer;
import fr.wijin.spring.jdbc.repository.CustomerDao;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private RowMapper<Customer> customerRowMapper;

    @Autowired
    public CustomerDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM CUSTOMERS";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<Customer> findAllCustomers() {
        String sql = "SELECT * FROM CUSTOMERS";
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Customer insertCustomer(Customer customer) {
        String sql = "INSERT INTO CUSTOMERS (FIRSTNAME, LASTNAME, MAIL, PHONE, COMPANY, ACTIVE, NOTES) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                customer.getFirstname(),
                customer.getLastname(),
                customer.getMail(),
                customer.getPhone(),
                customer.getCompany(),
                customer.getActive(),
                customer.getNotes());
        return customer;
    }

}
