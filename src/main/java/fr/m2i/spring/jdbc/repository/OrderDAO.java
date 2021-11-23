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

import fr.m2i.spring.jdbc.mapper.OrderRowMapper;
import fr.m2i.spring.jdbc.model.Order;

@Repository
public class OrderDAO {

	private JdbcTemplate jdbcTemplate;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
		jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);

		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

		simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("ORDERS");
	}

	/**
	 * Exercice 1
	 * 
	 * @return
	 */
	public int getCountOfOrders() {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ORDERS", Integer.class);
	}

	/**
	 * Exercice 2
	 * 
	 * @return
	 */
	public List<Order> getAllOrders() {
		return jdbcTemplate.query("SELECT * FROM ORDERS", new OrderRowMapper());
	}

	/**
	 * Exercice 3
	 * 
	 * @param id
	 * @return
	 */
	public int addOrder(final int id) {
		return jdbcTemplate.update("INSERT INTO ORDERS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", 
				id, "Commande 0", 450, 5, 20.0,
				"En cours", "Forfait", "Les notes de la commande", 1);
	}

	/**
	 * Exercice 4
	 * 
	 * @param emp
	 * @return
	 */
	public int addOrderUsingSimpleJdbcInsert(final Order order) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("ID", order.getId());
		parameters.put("LABEL", order.getLabel());
		parameters.put("ADR_ET", order.getAdrEt());
		parameters.put("NUMBER_OF_DAYS", order.getNumberOfDays());
		parameters.put("TVA", order.getTva());
		parameters.put("STATUS", order.getStatus());
		parameters.put("TYPE", order.getType());
		parameters.put("NOTES", order.getNotes());
		parameters.put("CUSTOMER_ID", order.getCustomer().getId());
		
		return simpleJdbcInsert.execute(parameters);
	}

	/**
	 * Exercice 5
	 * 
	 * @param id
	 * @return
	 */
	public Order getOrder(final int id) {
		final String query = "SELECT * FROM ORDERS WHERE ID = ?";
		return jdbcTemplate.queryForObject(query, new OrderRowMapper(), new Object[] { id });
	}

	/**
	 * Exercice 6 --> Execute ne retourne pas de résultat
	 */
	public void addOrderUsingExecuteMethod() {
		jdbcTemplate.execute("INSERT INTO ORDERS VALUES (6, 'Commande 6', 500, 3, 20.0, 'Terminee', 'Forfait', 'Les notes de la commande 6', 1)");
	}

	/**
	 * Exercice 7 avec NamedParameterJdbcTemplate
	 * 
	 * @return
	 */
	public String getOrderUsingMapSqlParameterSource() {
		final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", 1);

		return namedParameterJdbcTemplate.queryForObject("SELECT LABEL FROM ORDERS WHERE ID = :id",
				namedParameters, String.class);
	}

	/**
	 * Exercice 8 avec BeanPropertySqlParameterSource
	 * 
	 * @return
	 */
	public int getOrderUsingBeanPropertySqlParameterSource() {
		final Order order = new Order();
		order.setLabel("Formation Spring");

		final String SELECT_BY_LABEL = "SELECT COUNT(*) FROM ORDERS WHERE LABEL = :label";

		final SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(order);

		return namedParameterJdbcTemplate.queryForObject(SELECT_BY_LABEL, namedParameters, Integer.class);
	}

	/**
	 * Exercice 9 avec batchUpdate
	 * 
	 * @param employees
	 * @return
	 */
	public int[] batchUpdateUsingJDBCTemplate(final List<Order> orders) {
		return jdbcTemplate.batchUpdate("INSERT INTO ORDERS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {

			@Override
			public void setValues(final PreparedStatement ps, final int i) throws SQLException {
				ps.setInt(1, orders.get(i).getId());
				ps.setString(2, orders.get(i).getLabel());
				ps.setDouble(3, orders.get(i).getAdrEt());
				ps.setDouble(4, orders.get(i).getNumberOfDays());
				ps.setDouble(5, orders.get(i).getTva());
				ps.setString(6, orders.get(i).getStatus());
				ps.setString(7, orders.get(i).getType());
				ps.setString(8, orders.get(i).getNotes());
				ps.setInt(9, orders.get(i).getCustomer().getId());
			}

			@Override
			public int getBatchSize() {
				return orders.size();
			}
		});
	}

	/**
	 * Exercice 10 - Batch avec NamedParameterJdbcTemplate
	 * 
	 * @param employees
	 * @return
	 */
	public int[] batchUpdateUsingNamedParameterJDBCTemplate(final List<Order> orders) {
		final SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(orders.toArray());
		return namedParameterJdbcTemplate
				.batchUpdate("INSERT INTO ORDERS VALUES (:id, :label, :adrEt, :numberOfDays, :tva, :status, :type, :notes, :customer.id)", batch);
	}

}
