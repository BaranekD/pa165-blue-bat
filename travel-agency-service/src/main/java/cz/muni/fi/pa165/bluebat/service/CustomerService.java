package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;

import java.util.List;

/**
 * A service layer for Customer entity.
 * @author : Rudolf Madoran
 * @since : 7. 4. 2021, Wed
 **/
public interface CustomerService {

    /**
     * Delete a Customer in database
     * @param customer is Customer to be deleted
     */
    void deleteCustomer(Customer customer) throws WrongDataAccessException;

    /**
     * Create a new Customer
     * @param customer is Customer to be created
     */
    void addCustomer(Customer customer) throws WrongDataAccessException;

    /**
     * Update a new Customer
     * @param customer is Customer to be updated
     */
    void updateCustomer(Customer customer) throws WrongDataAccessException;

    /**
     * Find a Customer
     * @param id of Customer to be found
     * @return found Customer
     */
    Customer findCustomerById(Long id) throws WrongDataAccessException;

    /**
     * Find Customers
     * @return found Customers
     */
    List<Customer> findAllCustomers() throws WrongDataAccessException;
}
