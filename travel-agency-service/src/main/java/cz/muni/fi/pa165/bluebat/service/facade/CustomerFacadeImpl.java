package cz.muni.fi.pa165.bluebat.service.facade;

import cz.muni.fi.pa165.bluebat.dto.CustomerCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.CustomerDTO;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.facade.CustomerFacade;
import cz.muni.fi.pa165.bluebat.service.BeanMappingService;
import cz.muni.fi.pa165.bluebat.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void createCustomer(CustomerCreateDTO customerCreateDTO){
        Customer customer = beanMappingService.mapTo(customerCreateDTO, Customer.class);
        customerService.addCustomer(customer);
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO){
        Customer customer=beanMappingService.mapTo(customerDTO, Customer.class);
        customerService.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(Long id){
        Customer customer=customerService.findCustomerById(id);
        customerService.deleteCustomer(customer);
    }

    @Override
    public CustomerDTO getCustomerById(Long id){
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            return null;
        }
        CustomerDTO result = beanMappingService.mapTo(customer, CustomerDTO.class);
        return result;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return beanMappingService.mapTo(customerService.findAllCustomers(), CustomerDTO.class);
    }
}
