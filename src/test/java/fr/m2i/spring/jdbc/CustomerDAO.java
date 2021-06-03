package fr.m2i.spring.jdbc.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import fr.m2i.spring.jdbc.mapper.CustomerRowMapper;
import fr.m2i.spring.jdbc.model.Customer;

@Repository
public class CustomerDAO {

	private JdbcTemplate jdbcTemplate;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
		jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);

		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("CUSTOMERS");
	}

	/**
	 * Exercice 1
	 * 
	 * @return
	 */
	public int getCountOfCustomers() {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM CUSTOMERS", Integer.class);
	}

	/**
	 * Exercice 2
	 * 
	 * @return
	 */
	public List<Customer> getAllCustomers() {
		return jdbcTemplate.query("SELECT * FROM CUSTOMERS", new CustomerRowMapper());
	}

	/**
	 * Exercice 3
	 * 
	 * @param id
	 * @return
	 */
	public int addCustomer(final int id) {
		return jdbcTemplate.update("INSERT INTO CUSTOMERS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", 
				id, "James", "Bond", "MI6", "james.bond@mi6.uk",
				"0101010101", "0606060606", "Les notes de James", true);
	}

	/**
	 * Exercice 4
	 * 
	 * @param emp
	 * @return
	 */
	public int addCustomerUsingSimpleJdbcInsert(final Customer customer) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("ID", customer.getId());
		parameters.put("FIRST_NAME", customer.getFirstname());
		parameters.put("LAST_NAME", customer.getLastname());
		parameters.put("COMPANY", customer.getCompany());
		parameters.put("MAIL", customer.getMail());
		parameters.put("PHONE", customer.getPhone());
		parameters.put("MOBILE", customer.getMobile());
		parameters.put("NOTES", customer.getNotes());
		parameters.put("ACTIVE", customer.isActive());
		
		return simpleJdbcInsert.execute(parameters);
	}

	/**
	 * Exercice 5
	 * 
	 * @param id
	 * @return
	 */
	public Customer getCustomer(final int id) {
		final String query = "SELECT * FROM CUSTOMERS WHERE ID = ?";
		return jdbcTemplate.queryForObject(query, new CustomerRowMapper(), new Object[] { id });
	}

	/**
	 * Exercice 6 --> Execute ne retourne pas de résultat
	 */
	public void addCustomerUsingExecuteMethod() {
		jdbcTemplate.execute("INSERT INTO CUSTOMERS VALUES (6, 'James', 'Bond', 'MI6', 'james.bond@mi6.uk', '0101010101', '0606060606', 'Les notes de James', true)");
	}

	/**
	 * Exercice 7 avec NamedParameterJdbcTemplate
	 * 
	 * @return
	 */
	public String getCustomerUsingMapSqlParameterSource() {
		final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", 1);

		return namedParameterJdbcTemplate.queryForObject("SELECT FIRST_NAME FROM CUSTOMERS WHERE ID = :id",
				namedParameters, String.class);
	}

	/**
	 * Exercice 8 avec BeanPropertySqlParameterSource
	 * 
	 * @return
	 */
	public int getCustomerUsingBeanPropertySqlParameterSource() {
		final Customer customer = new Customer();
		customer.setFirstname("Indiana");

		final String SELECT_BY_FIRST_NAME = "SELECT COUNT(*) FROM CUSTOMERS WHERE FIRST_NAME = :firstname";

		final SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(customer);

		return namedParameterJdbcTemplate.queryForObject(SELECT_BY_FIRST_NAME, namedParameters, Integer.class);
	}

	/**
	 * Exercice 9 avec batchUpdate
	 * 
	 * @param employees
	 * @return
	 */
	public int[] batchUpdateUsingJDBCTemplate(final List<Customer> customers) {
		return jdbcTemplate.batchUpdate("INSERT INTO CUSTOMERS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {

			@Override
			public void setValues(final PreparedStatement ps, final int i) throws SQLException {
				ps.setInt(1, customers.get(i).getId());
				ps.setString(2, customers.get(i).getFirstname());
				ps.setString(3, customers.get(i).getLastname());
				ps.setString(4, customers.get(i).getCompany());
				ps.setString(5, customers.get(i).getMail());
				ps.setString(6, customers.get(i).getPhone());
				ps.setString(7, customers.get(i).getMobile());
				ps.setString(8, customers.get(i).getNotes());
				ps.setBoolean(9, customers.get(i).isActive());
			}

			@Override
			public int getBatchSize() {
				return 3;
			}
		});
	}

	/**
	 * Exercice 10 - Batch avec NamedParameterJdbcTemplate
	 * 
	 * @param employees
	 * @return
	 */
	public int[] batchUpdateUsingNamedParameterJDBCTemplate(final List<Customer> customers) {
		final SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(customers.toArray());
		return namedParameterJdbcTemplate
				.batchUpdate("INSERT INTO CUSTOMERS VALUES (:id, :firstname, :lastname, :company, :mail, :phone, :mobile, :notes, :active)", batch);
	}

}
