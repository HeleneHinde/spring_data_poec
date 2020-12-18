package fr.m2i.spring.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import fr.m2i.spring.jdbc.config.AppConfig;
import fr.m2i.spring.jdbc.model.Employee;
import fr.m2i.spring.jdbc.repository.EmployeeDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
public class EmployeeDAOTest {
	
	@Autowired
	private EmployeeDAO employeeDao;

	/**
	 * Exercice 1
	 */
	@Test
	public void testGetCountOfEmployees() {
		Assert.assertEquals(9, employeeDao.getCountOfEmployees());
	}

	/**
	 * Exercice 2
	 */
	@Test
	public void testQueryMethod() {
		Assert.assertEquals(4, employeeDao.getAllEmployees().size());
	}

	/**
	 * Exercice 3
	 */
	@Test
	public void testUpdateMethod() {
		Assert.assertEquals(1, employeeDao.addEmployee(5));
	}

	/**
	 * Exercice 4
	 */
	@Test
	public void testAddEmployeeUsingSimpleJdbcInsert() {
		final Employee emp = new Employee();
		emp.setId(11);
		emp.setFirstName("testFirstName");
		emp.setLastName("testLastName");
		emp.setAddress("testAddress");

		Assert.assertEquals(1, employeeDao.addEmployeeUsingSimpleJdbcInsert(emp));
	}

	/**
	 * Exercice 6
	 */
	@Test
	public void testExecuteMethod() {
		employeeDao.addEmployeeUsingExecuteMethod();
	}

	/**
	 * Exercice 7
	 */
	@Test
	public void testMapSqlParameterSource() {
		Assert.assertEquals("Jean", employeeDao.getEmployeeUsingMapSqlParameterSource());
	}

	/**
	 * Exercice 8
	 */
	@Test
	public void testBeanPropertySqlParameterSource() {
		Assert.assertEquals(1, employeeDao.getEmployeeUsingBeanPropertySqlParameterSource());
	}

	/**
	 * Exercice Exception
	 */
	@Test
	public void testCustomExceptionTranslator() {
		employeeDao.addEmployee(7);

		try {
			employeeDao.addEmployee(7);
		} catch (final DuplicateKeyException e) {
			System.out.println(e.getMessage());
			Assert.assertTrue(e.getMessage().contains("Custom Exception translator - Integrity contraint violation."));
		}
	}

	/**
	 * Exercice 9
	 */
	@Test
	public void testBatchUpdateUsingJDBCTemplate() {
		final List<Employee> employees = new ArrayList<Employee>();
		final Employee emp1 = new Employee();
		emp1.setId(10);
		emp1.setFirstName("firstName1");
		emp1.setLastName("lastName1");
		emp1.setAddress("address1");

		final Employee emp2 = new Employee();
		emp2.setId(20);
		emp2.setFirstName("firstName2");
		emp2.setLastName("lastName2");
		emp2.setAddress("address2");

		final Employee emp3 = new Employee();
		emp3.setId(30);
		emp3.setFirstName("firstName3");
		emp3.setLastName("lastName3");
		emp3.setAddress("address3");

		employees.add(emp1);
		employees.add(emp2);
		employees.add(emp3);

		employeeDao.batchUpdateUsingJDBCTemplate(employees);

		Assert.assertNotNull(employeeDao.getEmployee(10));
		Assert.assertNotNull(employeeDao.getEmployee(20));
		Assert.assertNotNull(employeeDao.getEmployee(30));
	}

	/**
	 * Exercice 10
	 */
	@Test
	public void testBatchUpdateUsingNamedParameterJDBCTemplate() {
		final List<Employee> employees = new ArrayList<Employee>();
		final Employee emp1 = new Employee();
		emp1.setId(40);
		emp1.setFirstName("firstName4");
		emp1.setLastName("lastName4");
		emp1.setAddress("address4");

		final Employee emp2 = new Employee();
		emp2.setId(50);
		emp2.setFirstName("firstName5");
		emp2.setLastName("lastName5");
		emp2.setAddress("address5");

		final Employee emp3 = new Employee();
		emp3.setId(60);
		emp3.setFirstName("firstName6");
		emp3.setLastName("lastName6");
		emp3.setAddress("address6");

		employees.add(emp1);
		employees.add(emp2);
		employees.add(emp3);

		employeeDao.batchUpdateUsingNamedParameterJDBCTemplate(employees);

		Assert.assertNotNull(employeeDao.getEmployee(40));
		Assert.assertNotNull(employeeDao.getEmployee(50));
		Assert.assertNotNull(employeeDao.getEmployee(60));
	}

}
