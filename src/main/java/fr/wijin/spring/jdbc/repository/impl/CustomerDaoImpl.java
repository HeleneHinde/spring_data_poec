package fr.wijin.spring.jdbc.repository.impl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import fr.wijin.spring.jdbc.model.Customer;
import fr.wijin.spring.jdbc.repository.CustomerDao;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    private final SimpleJdbcInsert customerInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private RowMapper<Customer> customerRowMapper;

    @Autowired
    public CustomerDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.customerInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("CUSTOMERS")
                .usingGeneratedKeyColumns("ID");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
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

    @Override
    public Customer insertSimpleCustomer(Customer customer) {
        Number newId = customerInsert.executeAndReturnKey(getSqlParams(customer));
        customer.setId(newId.intValue());
        return customer;
    }

    private Map<String, ?> getSqlParams(Customer customer) {
        return Map.of(
                "FIRSTNAME", customer.getFirstname(),
                "LASTNAME", customer.getLastname(),
                "MAIL", customer.getMail(),
                "PHONE", customer.getPhone(),
                "COMPANY", customer.getCompany(),
                "ACTIVE", customer.getActive(),
                "NOTES", customer.getNotes()
        );
    }

    @Override
    public Customer findCustomerById(int id) {
        String sql = "SELECT * FROM CUSTOMERS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, customerRowMapper, id);
    }

    @Override
    public Customer findCustomerByIdNamedParam(int id) {
        String sql = "SELECT * FROM CUSTOMERS WHERE ID = :id";
        return namedParameterJdbcTemplate.queryForObject(sql, Map.of("id", id), customerRowMapper);
    }

    @Override
    public List<Customer> updateAllCustomersName(String newName, List<Customer> customers) {

        for(Customer customer : customers){
            customer.setLastname(newName+' '+customer.getLastname());
        }
        
        SqlParameterSource [] batch = SqlParameterSourceUtils.createBatch(customers.toArray());
        String sql = "update CUSTOMERS set LASTNAME = :lastname";
       int [] updateCount = namedParameterJdbcTemplate.batchUpdate(sql, batch);

        return customers;
    }

    
}
