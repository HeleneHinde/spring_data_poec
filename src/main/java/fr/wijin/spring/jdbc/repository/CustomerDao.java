package fr.wijin.spring.jdbc.repository;

import java.util.List;

import fr.wijin.spring.jdbc.model.Customer;

public interface CustomerDao {

    public int getCount();

    public List<Customer> findAllCustomers();

    public Customer insertCustomer(Customer customer);

}
