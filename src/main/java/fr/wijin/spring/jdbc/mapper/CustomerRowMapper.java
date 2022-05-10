package fr.wijin.spring.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.wijin.spring.jdbc.model.Customer;

public class CustomerRowMapper implements RowMapper<Customer> {

	@Override
	public Customer mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final Customer customer = new Customer();

		customer.setId(rs.getInt("ID"));
		customer.setFirstname(rs.getString("FIRST_NAME"));
		customer.setLastname(rs.getString("LAST_NAME"));
		customer.setCompany(rs.getString("COMPANY"));
		customer.setMail(rs.getString("MAIL"));
		customer.setPhone(rs.getString("PHONE"));
		customer.setMobile(rs.getString("MOBILE"));
		customer.setNotes(rs.getString("NOTES"));
		customer.setActive(rs.getBoolean("ACTIVE"));

		return customer;
	}

}