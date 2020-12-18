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

import fr.m2i.spring.jdbc.mapper.EmployeeRowMapper;
import fr.m2i.spring.jdbc.model.Employee;

@Repository
public class EmployeeDAO {

	private JdbcTemplate jdbcTemplate;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private SimpleJdbcInsert simpleJdbcInsert;

	@Autowired
	public void setDataSource(final DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
		final CustomSQLErrorCodeTranslator customSQLErrorCodeTranslator = new CustomSQLErrorCodeTranslator();
		jdbcTemplate.setExceptionTranslator(customSQLErrorCodeTranslator);
		
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("EMPLOYEE");
	}

	/**
	 * Exercice 1
	 * @return
	 */
	public int getCountOfEmployees() {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM EMPLOYEE", Integer.class);
	}

	/**
	 * Exercice 2
	 * @return
	 */
	public List<Employee> getAllEmployees() {
		return jdbcTemplate.query("SELECT * FROM EMPLOYEE", new EmployeeRowMapper());
	}

	/**
	 * Exercice 3
	 * @param id
	 * @return
	 */
	public int addEmployee(final int id) {
		return jdbcTemplate.update("INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?)", id, "Bill", "Gates", "USA");
	}

	/**
	 * Exercice 4
	 * @param emp
	 * @return
	 */
	public int addEmployeeUsingSimpleJdbcInsert(final Employee emp) {
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("ID", emp.getId());
		parameters.put("FIRST_NAME", emp.getFirstName());
		parameters.put("LAST_NAME", emp.getLastName());
		parameters.put("ADDRESS", emp.getAddress());

		return simpleJdbcInsert.execute(parameters);
	}

	/**
	 * Exercice 5
	 * @param id
	 * @return
	 */
	public Employee getEmployee(final int id) {
		final String query = "SELECT * FROM EMPLOYEE WHERE ID = ?";
		return jdbcTemplate.queryForObject(query, new EmployeeRowMapper(), new Object[] { id });
	}

	/**
	 * Exercice 6
	 * --> Execute ne retourne pas de résultat
	 */
	public void addEmployeeUsingExecuteMethod() {
		jdbcTemplate.execute("INSERT INTO EMPLOYEE VALUES (6, 'Bill', 'Gates', 'USA')");
	}

	/**
	 * Exercice 7 avec NamedParameterJdbcTemplate
	 * @return
	 */
	public String getEmployeeUsingMapSqlParameterSource() {
		final SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", 1);

		return namedParameterJdbcTemplate.queryForObject("SELECT FIRST_NAME FROM EMPLOYEE WHERE ID = :id",
				namedParameters, String.class);
	}

	/**
	 * Exercice 8 avec BeanPropertySqlParameterSource
	 * @return
	 */
	public int getEmployeeUsingBeanPropertySqlParameterSource() {
		final Employee employee = new Employee();
		employee.setFirstName("Jean");

		final String SELECT_BY_ID = "SELECT COUNT(*) FROM EMPLOYEE WHERE FIRST_NAME = :firstName";

		final SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(employee);

		return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, Integer.class);
	}

	/**
	 * Exercice 9 avec batchUPdate
	 * @param employees
	 * @return
	 */
	public int[] batchUpdateUsingJDBCTemplate(final List<Employee> employees) {
		return jdbcTemplate.batchUpdate("INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?)", new BatchPreparedStatementSetter() {

			@Override
			public void setValues(final PreparedStatement ps, final int i) throws SQLException {
				ps.setInt(1, employees.get(i).getId());
				ps.setString(2, employees.get(i).getFirstName());
				ps.setString(3, employees.get(i).getLastName());
				ps.setString(4, employees.get(i).getAddress());
			}

			@Override
			public int getBatchSize() {
				return 3;
			}
		});
	}

	/**
	 * Exercice 10 - Batch avec NamedParameterJdbcTemplate
	 * @param employees
	 * @return
	 */
	public int[] batchUpdateUsingNamedParameterJDBCTemplate(final List<Employee> employees) {
		final SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(employees.toArray());
		final int[] updateCounts = namedParameterJdbcTemplate
				.batchUpdate("INSERT INTO EMPLOYEE VALUES (:id, :firstName, :lastName, :address)", batch);
		return updateCounts;
	}

}
