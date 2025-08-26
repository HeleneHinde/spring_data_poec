package fr.wijin.spring.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import fr.wijin.spring.jdbc.model.Customer;

 @Component
public class CustomerRowMapper implements RowMapper <Customer>{

    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("ID"));
        customer.setFirstname(rs.getString("FIRSTNAME"));
        customer.setLastname(rs.getString("LASTNAME"));
        customer.setMail(rs.getString("MAIL"));
        customer.setPhone(rs.getString("PHONE"));
        customer.setCompany(rs.getString("COMPANY"));
        customer.setActive(rs.getBoolean("ACTIVE"));
        customer.setNotes(rs.getString("NOTES"));
        return customer;
    }
}
