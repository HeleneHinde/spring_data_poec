package fr.wijin.spring.jdbc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.wijin.spring.jdbc.model.Customer;
import fr.wijin.spring.jdbc.repository.CustomerDao;

@Service
public class CustomerService {

	private final CustomerDao customerDao;
	
	@Autowired
	public CustomerService(CustomerDao dao) {
		this.customerDao = dao;
	}

	public int getCustomerCount() {
		return customerDao.getCount();
	}

	public List<Customer> getAllCustomers() {
		return customerDao.findAllCustomers();
	}

	public Customer insertCustomer(Customer customer) {
		return customerDao.insertCustomer(customer);
	}

	public Customer insertSimpleCustomer(Customer customer) {
		return customerDao.insertSimpleCustomer(customer);
	}

	public Customer getCustomerById(int id) {
		return customerDao.findCustomerById(id);
	}

	public List<Customer> updateAllCustomersName(String newName, List<Customer> customers) {
		return customerDao.updateAllCustomersName(newName, customers);
	}

}
