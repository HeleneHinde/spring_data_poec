package fr.wijin.spring.jdbc.repository;

import java.util.List;

import fr.wijin.spring.jdbc.model.Customer;

public interface CustomerDao {

    public int getCount();

    public List<Customer> findAllCustomers();

    public Customer insertCustomer(Customer customer);

    public Customer insertSimpleCustomer(Customer customer);

    public Customer findCustomerById(int id);

     public Customer findCustomerByIdNamedParam(int id);

     public List<Customer> updateAllCustomersName(String newName, List<Customer> customers);

}
