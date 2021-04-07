package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.entity.Customer;

import java.util.List;

/**
 * @author rudolf
 */
public interface CustomerDao {

    /**
     * Create a new Customer in database
     * @param customer is Customer to be created
     */
    void create(Customer customer);

    /**
     * Delete a Customer in database
     * @param customer is Customer to be deleted
     */
    void delete(Customer customer);

    /**
     * Update a Customer in database
     * @param customer is Customer to be updated
     */
    void update(Customer customer);

    /**
     * Find a Customer in database
     * @param id of Customer to be found
     * @return found Customer
     */
    Customer findById(Long id);

    /**
     * Find all Customers in database
     * @return found Customers
     */
    List<Customer> findAll();

}
