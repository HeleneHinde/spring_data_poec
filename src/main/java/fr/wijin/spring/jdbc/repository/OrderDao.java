package fr.wijin.spring.jdbc.repository;

import java.util.List;

import fr.wijin.spring.jdbc.model.Order;

public interface OrderDao {

	/**
	 * Exercice 1
	 * 
	 * @return
	 */
	public int getCountOfOrders();

	/**
	 * Exercice 2
	 * 
	 * @return
	 */
	public List<Order> getAllOrders();

	/**
	 * Exercice 3
	 * 
	 * @param id
	 * @return
	 */
	public int addOrder(final int id);

	/**
	 * Exercice 4
	 * 
	 * @param emp
	 * @return
	 */
	public int addOrderUsingSimpleJdbcInsert(final Order order);

	/**
	 * Exercice 5
	 * 
	 * @param id
	 * @return
	 */
	public Order getOrder(final int id);

	/**
	 * Exercice 6 --> Execute ne retourne pas de résultat
	 */
	public void addOrderUsingExecuteMethod();

	/**
	 * Exercice 7 avec NamedParameterJdbcTemplate
	 * 
	 * @return
	 */
	public String getOrderUsingMapSqlParameterSource();

	/**
	 * Exercice 8 avec BeanPropertySqlParameterSource
	 * 
	 * @return
	 */
	public int getOrderUsingBeanPropertySqlParameterSource();

	/**
	 * Exercice 9 avec batchUpdate
	 * 
	 * @param employees
	 * @return
	 */
	public int[] batchUpdateUsingJDBCTemplate(final List<Order> orders);

	/**
	 * Exercice 10 - Batch avec NamedParameterJdbcTemplate
	 * 
	 * @param employees
	 * @return
	 */
	public int[] batchUpdateUsingNamedParameterJDBCTemplate(final List<Order> orders);

}
