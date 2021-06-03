package fr.m2i.spring.jdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.m2i.spring.jdbc.model.Customer;
import fr.m2i.spring.jdbc.model.Order;

public class OrderRowMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		final Order order = new Order();

		order.setId(rs.getInt("ID"));
		order.setLabel(rs.getString("LABEL"));
		order.setAdrEt(rs.getDouble("ADR_ET"));
		order.setNumberOfDays(rs.getDouble("NUMBER_OF_DAYS"));
		order.setTva(rs.getDouble("TVA"));
		order.setType(rs.getString("TYPE"));
		order.setNotes(rs.getString("NOTES"));
		Customer customer = new Customer();
		customer.setId(rs.getInt("CUSTOMER_ID"));
		order.setCustomer(customer);

		return order;
	}

}