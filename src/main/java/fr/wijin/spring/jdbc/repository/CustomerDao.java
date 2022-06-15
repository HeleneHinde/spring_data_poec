package fr.wijin.spring.jdbc.repository;

import java.util.List;

import fr.wijin.spring.jdbc.model.Customer;

public interface CustomerDao {

	/**
	 * Exercice 1
	 * 
	 * @return
	 */
	public int getCountOfCustomers();

	/**
	 * Exercice 2
	 * 
	 * @return
	 */
	public List<Customer> getAllCustomers();

	/**
	 * Exercice 3
	 * 
	 * @param id
	 * @return
	 */
	public int addCustomer(final int id);

	/**
	 * Exercice 4
	 * 
	 * @param emp
	 * @return
	 */
	public int addCustomerUsingSimpleJdbcInsert(final Customer customer);

	/**
	 * Exercice 5
	 * 
	 * @param id
	 * @return
	 */
	public Customer getCustomer(final int id);

	/**
	 * Exercice 6 --> Execute ne retourne pas de résultat
	 */
	public void addCustomerUsingExecuteMethod();

	/**
	 * Exercice 7 avec NamedParameterJdbcTemplate
	 * 
	 * @return
	 */
	public String getCustomerUsingMapSqlParameterSource();

	/**
	 * Exercice 8 avec BeanPropertySqlParameterSource
	 * 
	 * @return
	 */
	public int getCustomerUsingBeanPropertySqlParameterSource();

	/**
	 * Exercice 9 avec batchUpdate
	 * 
	 * @param employees
	 * @return
	 */
	public int[] batchUpdateUsingJDBCTemplate(final List<Customer> customers);

	/**
	 * Exercice 10 - Batch avec NamedParameterJdbcTemplate
	 * 
	 * @param employees
	 * @return
	 */
	public int[] batchUpdateUsingNamedParameterJDBCTemplate(final List<Customer> customers);

}
