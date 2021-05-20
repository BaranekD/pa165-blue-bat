package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dto.CustomerCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.CustomerDTO;
import java.util.List;

/**
 * @author Ondrej Vaca
 */

public interface CustomerFacade {

    /**
     * Create a new Customer in system
     * @param customerCreateDTO Customer DTO to be created
     */
    CustomerDTO createCustomer(CustomerCreateDTO customerCreateDTO);

    /**
     * Update a customer in system
     * @param customerDTO Customer DTO to be updated
     */
    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    /**
     * Delete a customer in system
     * @param id Long id of customer to be deleted
     */
    void deleteCustomer(Long id);

    /**
     * Find a customer in system
     * @param id Long id of customer to be found
     * @return Customer DTO if exists, null otherwise
     */
    CustomerDTO getCustomerById(Long id);

    /**
     * Find all customers in system
     * @return List of Customer DTOs
     */
    List<CustomerDTO> getAllCustomers();
}
