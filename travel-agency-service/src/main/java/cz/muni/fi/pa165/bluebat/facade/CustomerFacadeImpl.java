package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dto.CustomerCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.CustomerDTO;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.facade.CustomerFacade;
import cz.muni.fi.pa165.bluebat.service.BeanMappingService;
import cz.muni.fi.pa165.bluebat.service.CustomerService;
import cz.muni.fi.pa165.bluebat.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author Ondrej Vaca
 */

@Service
@Transactional
public class CustomerFacadeImpl implements CustomerFacade {

    private CustomerService customerService;

    private final BeanMappingService beanMappingService;

    @Autowired
    public CustomerFacadeImpl(CustomerService customerService, BeanMappingService beanMappingService) {
        this.customerService =  customerService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public CustomerDTO createCustomer(CustomerCreateDTO customerCreateDTO){
        Validator.NotNull(customerCreateDTO,"CustomerCreateDTO");
        Customer customer = beanMappingService.mapTo(customerCreateDTO, Customer.class);
        customerService.addCustomer(customer);
        return beanMappingService.mapTo(customer, CustomerDTO.class);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO){
        Validator.NotNull(customerDTO,"CustomerDTO");
        Customer customer=beanMappingService.mapTo(customerDTO, Customer.class);
        customerService.updateCustomer(customer);
        return beanMappingService.mapTo(customer, CustomerDTO.class);
    }

    @Override
    public void deleteCustomer(Long id){
        Validator.Positive(id, "Customer id");
        Customer customer=customerService.findCustomerById(id);
        customerService.deleteCustomer(customer);
    }

    @Override
    public CustomerDTO getCustomerById(Long id){
        Validator.Positive(id, "Customer id");
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            return null;
        }

        return beanMappingService.mapTo(customer, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return beanMappingService.mapTo(customerService.findAllCustomers(),CustomerDTO.class);
    }
}
