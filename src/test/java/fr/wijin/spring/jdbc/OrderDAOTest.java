package fr.wijin.spring.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import fr.wijin.spring.jdbc.config.AppConfig;
import fr.wijin.spring.jdbc.model.Customer;
import fr.wijin.spring.jdbc.model.Order;
import fr.wijin.spring.jdbc.repository.OrderDAO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { AppConfig.class }, loader = AnnotationConfigContextLoader.class)
class OrderDAOTest {

	@Autowired
	private OrderDAO orderDao;

	/**
	 * Exercice 1
	 */
	@Test
	void testGetCountOfOrders() {
		Assertions.assertEquals(8, orderDao.getCountOfOrders());
	}

	/**
	 * Exercice 2
	 */
	@Test
	void testQueryMethod() {
		Assertions.assertEquals(3, orderDao.getAllOrders().size());
	}

	/**
	 * Exercice 3
	 */
	@Test
	void testUpdateMethod() {
		Assertions.assertEquals(1, orderDao.addOrder(5));
	}

	/**
	 * Exercice 4
	 */
	@Test
	void testAddOrderUsingSimpleJdbcInsert() {
		final Order order = new Order();
		order.setId(11);
		order.setLabel("testLAbel");
		order.setAdrEt(450.0);
		order.setNumberOfDays(5.0);
		order.setTva(20.0);
		order.setStatus("testStatus");
		order.setType("testType");
		order.setNotes("testNotes");
		Customer customer = new Customer();
		customer.setId(1);
		order.setCustomer(customer);

		Assertions.assertEquals(1, orderDao.addOrderUsingSimpleJdbcInsert(order));
	}

	/**
	 * Exercice 6
	 */
	@Test
	void testExecuteMethod() {
		try {
			orderDao.addOrderUsingExecuteMethod();
		} catch (Exception e) {
			Assertions.fail();
		}
	}

	/**
	 * Exercice 7
	 */
	@Test
	void testMapSqlParameterSource() {
		Assertions.assertEquals("Formation Java", orderDao.getOrderUsingMapSqlParameterSource());
	}

	/**
	 * Exercice 8
	 */
	@Test
	void testBeanPropertySqlParameterSource() {
		Assertions.assertEquals(1, orderDao.getOrderUsingBeanPropertySqlParameterSource());
	}

	/**
	 * Exercice Exception
	 */
	@Test
	void testCustomExceptionTranslator() {
		orderDao.addOrder(7);

		try {
			orderDao.addOrder(7);
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
		final List<Order> orders = new ArrayList<Order>();
		final Order order = new Order();
		order.setId(10);
		order.setLabel("label1");
		order.setAdrEt(450.0);
		order.setNumberOfDays(3.0);
		order.setTva(20.0);
		order.setStatus("status1");
		order.setType("type1");
		order.setNotes("notes1");
		Customer customer = new Customer();
		customer.setId(1);
		order.setCustomer(customer);

		final Order order2 = new Order();
		order2.setId(20);
		order2.setLabel("label2");
		order2.setAdrEt(450.0);
		order2.setNumberOfDays(3.0);
		order2.setTva(20.0);
		order2.setStatus("status2");
		order2.setType("type2");
		order2.setNotes("notes2");
		Customer customer2 = new Customer();
		customer2.setId(2);
		order2.setCustomer(customer2);

		final Order order3 = new Order();
		order3.setId(30);
		order3.setLabel("label3");
		order3.setAdrEt(450.0);
		order3.setNumberOfDays(3.0);
		order3.setTva(20.0);
		order3.setStatus("status3");
		order3.setType("type3");
		order3.setNotes("notes3");
		Customer customer3 = new Customer();
		customer3.setId(3);
		order3.setCustomer(customer3);

		orders.add(order);
		orders.add(order2);
		orders.add(order3);

		orderDao.batchUpdateUsingJDBCTemplate(orders);

		Assertions.assertNotNull(orderDao.getOrder(10));
		Assertions.assertNotNull(orderDao.getOrder(20));
		Assertions.assertNotNull(orderDao.getOrder(30));
	}

	/**
	 * Exercice 10
	 */
	@Test
	void testBatchUpdateUsingNamedParameterJDBCTemplate() {
		final List<Order> orders = new ArrayList<Order>();
		final Order order1 = new Order();
		order1.setId(40);
		order1.setLabel("label4");
		order1.setAdrEt(450.0);
		order1.setNumberOfDays(3.0);
		order1.setTva(20.0);
		order1.setStatus("status4");
		order1.setType("type4");
		order1.setNotes("notes4");
		Customer customer = new Customer();
		customer.setId(1);
		order1.setCustomer(customer);

		final Order order2 = new Order();
		order2.setId(50);
		order2.setLabel("label5");
		order2.setAdrEt(450.0);
		order2.setNumberOfDays(3.0);
		order2.setTva(20.0);
		order2.setStatus("status5");
		order2.setType("type5");
		order2.setNotes("notes5");
		Customer customer2 = new Customer();
		customer.setId(2);
		order2.setCustomer(customer2);

		final Order order3 = new Order();
		order3.setId(60);
		order3.setLabel("label6");
		order3.setAdrEt(450.0);
		order3.setNumberOfDays(3.0);
		order3.setTva(20.0);
		order3.setStatus("status6");
		order3.setType("type6");
		order3.setNotes("notes6");
		Customer customer3 = new Customer();
		customer.setId(3);
		order3.setCustomer(customer3);
		
		orders.add(order1);
		orders.add(order2);
		orders.add(order3);

		orderDao.batchUpdateUsingNamedParameterJDBCTemplate(orders);

		Assertions.assertNotNull(orderDao.getOrder(40));
		Assertions.assertNotNull(orderDao.getOrder(50));
		Assertions.assertNotNull(orderDao.getOrder(60));
	}

}
