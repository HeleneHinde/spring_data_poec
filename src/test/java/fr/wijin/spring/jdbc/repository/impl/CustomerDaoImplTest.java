package fr.wijin.spring.jdbc.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import fr.wijin.spring.jdbc.config.AppConfig;
import fr.wijin.spring.jdbc.model.Customer;
import fr.wijin.spring.jdbc.repository.CustomerDao;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
@TestMethodOrder(OrderAnnotation.class)
class CustomerDaoImplTest {

	@Autowired
	private CustomerDao customerDao;

	/**
	 * Exercice 1
	 */
	@Test
	@Order(1)
	void testGetCountOfCustomers() {
		Assertions.assertEquals(4, customerDao.getCountOfCustomers());
	}

	/**
	 * Exercice 2
	 */
	@Test
	@Order(2)
	void testQueryMethod() {
		Assertions.assertEquals(4, customerDao.getAllCustomers().size());
	}

	/**
	 * Exercice 3
	 */
	@Test
	void testUpdateMethod() {
		Assertions.assertEquals(1, customerDao.addCustomer(5));
	}

	/**
	 * Exercice 4
	 */
	@Test
	void testAddCustomerUsingSimpleJdbcInsert() {
		final Customer customer = new Customer();
		customer.setId(11);
		customer.setFirstname("testFirstName");
		customer.setLastname("testLastName");
		customer.setCompany("testCompany");
		customer.setMail("testMail");
		customer.setPhone("testPhone");
		customer.setMobile("testMobile");
		customer.setNotes("testNotes");
		customer.setActive(true);

		Assertions.assertEquals(1, customerDao.addCustomerUsingSimpleJdbcInsert(customer));
	}

	/**
	 * Exercice 6
	 */
	@Test
	void testExecuteMethod() {
		try {
			customerDao.addCustomerUsingExecuteMethod();
		} catch (Exception e) {
			Assertions.fail();
		}
	}

	/**
	 * Exercice 7
	 */
	@Test
	void testMapSqlParameterSource() {
		Assertions.assertEquals("Indiana", customerDao.getCustomerUsingMapSqlParameterSource());
	}

	/**
	 * Exercice 8
	 */
	@Test
	void testBeanPropertySqlParameterSource() {
		Assertions.assertEquals(1, customerDao.getCustomerUsingBeanPropertySqlParameterSource());
	}

	/**
	 * Exercice Exception
	 */
	@Test
	void testCustomExceptionTranslator() {
		customerDao.addCustomer(7);

		try {
			customerDao.addCustomer(7);
		} catch (final DuplicateKeyException e) {
			System.out.println(e.getMessage());
			Assertions.assertTrue(
					e.getMessage().contains("Custom Exception translator - Integrity contraint violation."));
		}
	}

	/**
	 * Exercice 9
	 */
	@Test
	void testBatchUpdateUsingJDBCTemplate() {
		final List<Customer> customers = new ArrayList<Customer>();
		final Customer customer = new Customer();
		customer.setId(10);
		customer.setFirstname("firstName1");
		customer.setLastname("lastName1");
		customer.setCompany("company1");
		customer.setMail("mail1");
		customer.setPhone("phone1");
		customer.setMobile("mobile1");
		customer.setNotes("notes1");
		customer.setActive(true);

		final Customer customer2 = new Customer();
		customer2.setId(20);
		customer2.setFirstname("firstName2");
		customer2.setLastname("lastName2");
		customer2.setCompany("company2");
		customer2.setMail("mail2");
		customer2.setPhone("phone2");
		customer2.setMobile("mobile2");
		customer2.setNotes("notes2");
		customer2.setActive(true);

		final Customer customer3 = new Customer();
		customer3.setId(30);
		customer3.setFirstname("firstName3");
		customer3.setLastname("lastName3");
		customer3.setCompany("company3");
		customer3.setMail("mail3");
		customer3.setPhone("phone3");
		customer3.setMobile("mobile3");
		customer3.setNotes("notes3");
		customer3.setActive(true);

		customers.add(customer);
		customers.add(customer2);
		customers.add(customer3);

		customerDao.batchUpdateUsingJDBCTemplate(customers);

		Assertions.assertNotNull(customerDao.getCustomer(10));
		Assertions.assertNotNull(customerDao.getCustomer(20));
		Assertions.assertNotNull(customerDao.getCustomer(30));
	}

	/**
	 * Exercice 10
	 */
	@Test
	void testBatchUpdateUsingNamedParameterJDBCTemplate() {
		final List<Customer> customers = new ArrayList<Customer>();
		final Customer customer1 = new Customer();
		customer1.setId(40);
		customer1.setFirstname("firstName4");
		customer1.setLastname("lastName4");
		customer1.setCompany("company4");
		customer1.setMail("mail4");
		customer1.setPhone("phone4");
		customer1.setMobile("mobile4");
		customer1.setNotes("notes4");
		customer1.setActive(true);

		final Customer customer2 = new Customer();
		customer2.setId(50);
		customer2.setFirstname("firstName5");
		customer2.setLastname("lastName5");
		customer2.setCompany("company5");
		customer2.setMail("mail5");
		customer2.setPhone("phone5");
		customer2.setMobile("mobile5");
		customer2.setNotes("notes5");
		customer2.setActive(true);

		final Customer customer3 = new Customer();
		customer3.setId(60);
		customer3.setFirstname("firstName6");
		customer3.setLastname("lastName6");
		customer3.setCompany("company6");
		customer3.setMail("mail6");
		customer3.setPhone("phone6");
		customer3.setMobile("mobile6");
		customer3.setNotes("notes6");
		customer3.setActive(true);

		customers.add(customer1);
		customers.add(customer2);
		customers.add(customer3);

		customerDao.batchUpdateUsingNamedParameterJDBCTemplate(customers);

		Assertions.assertNotNull(customerDao.getCustomer(40));
		Assertions.assertNotNull(customerDao.getCustomer(50));
		Assertions.assertNotNull(customerDao.getCustomer(60));
	}

}
