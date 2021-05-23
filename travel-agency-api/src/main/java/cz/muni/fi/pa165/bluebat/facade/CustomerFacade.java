package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dto.CustomerAuthenticateDTO;
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

    /**
     * Try to authenticate a customer.
     *
     * @param  customerAuthenticateDTO CustomerAuthenticateDTO object to authenticate
     * @return true, on if the customer was authenticated
     */
    boolean authenticate(CustomerAuthenticateDTO customerAuthenticateDTO);

    /**
     * Check if the given customer is admin.
     *
     * @param customerDTO CustomerDTO object to test
     * @return true, only if the customer is administrator.
     */
    boolean isAdmin(CustomerDTO customerDTO);

    /**
     * Find a customer in system
     * @param nickName String nickName of customer to be found
     * @return Customer DTO if exists, null otherwise
     */
    CustomerDTO getCustomerByNickName(String nickName);
}
