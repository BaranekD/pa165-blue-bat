package cz.muni.fi.pa165.bluebat.service;


import cz.muni.fi.pa165.bluebat.entity.Customer;

import java.util.List;
import java.util.Set;


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
    void deleteCustomer(Customer customer);

    /**
     * Create a new Customer
     * @param customer is Customer to be created
     */
    void addCustomer(Customer customer);

    /**
     * Update a new Customer
     * @param customer is Customer to be updated
     */
    void updateCustomer(Customer customer);

    /**
     * Find a Customer
     * @param id of Customer to be found
     * @return found Customer
     */
    Customer findCustomerById(Long id);

    /**
     * Find Customers
     * @return found Customers
     */
    List<Customer> findAllCustomers();

}
