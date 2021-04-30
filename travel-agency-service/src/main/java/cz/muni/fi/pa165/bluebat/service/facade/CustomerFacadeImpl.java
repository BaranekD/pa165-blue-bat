package cz.muni.fi.pa165.bluebat.service.facade;

import cz.muni.fi.pa165.bluebat.dto.CustomerDTO;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.facade.CustomerFacade;
import cz.muni.fi.pa165.bluebat.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerFacadeImpl implements CustomerFacade {

    @Autowired
    private CustomerService customerService;

    @Override
    public void createCustomer(CustomerDTO customerDTO){
        Customer customer=mapCustomerDTOToCustomer(customerDTO);
        customerService.addCustomer(customer);
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO){
        Customer customer=mapCustomerDTOToCustomer(customerDTO);
        customerService.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(Long id){
        Customer customer=customerService.findCustomerById(id);
        customerService.deleteCustomer(customer);
    }

    @Override
    public CustomerDTO getCustomerById(Long id){
        Customer customer=customerService.findCustomerById(id);
        CustomerDTO customerDTO=mapCustomerToCustomerDTO(customer);
        return customerDTO;
    }

    @Override
    List<CustomerDTO> getAllCustomers(){

    }



    private Customer mapCustomerDTOToCustomer(CustomerDTO customerDTO){
        Customer customer=new Customer();

        customer.setSurname(customerDTO.getSurname());
        customer.setAddress(customerDTO.getSurname());
        customer.setEmail(customerDTO.getEmail());
        customer.setBirthday(customerDTO.getBirthday());
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setReservation(customerDTO.getReservation());

        return  customer;
    }

    private CustomerDTO mapCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO=new CustomerDTO();

        customerDTO.setSurname(customer.getSurname());
        customerDTO.setAddress(customer.getSurname());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setBirthday(customer.getBirthday());
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setReservation(customer.getReservation());

        return customerDTO;
    }


}

