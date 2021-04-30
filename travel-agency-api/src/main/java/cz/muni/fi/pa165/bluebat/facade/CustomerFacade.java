package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dto.CustomerCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.CustomerDTO;

import java.util.List;

public interface CustomerFacade {
    void createCustomer(CustomerCreateDTO customerCreateDTO);

    void updateCustomer(CustomerDTO excursionDTO);

    void deleteCustomer(Long id);

    CustomerDTO getCustomerById(Long id);

    List<CustomerDTO> getAllCustomers();
}
