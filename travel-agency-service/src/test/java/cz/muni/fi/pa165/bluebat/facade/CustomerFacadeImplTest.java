package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dto.CustomerCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.CustomerDTO;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.facade.CustomerFacade;
import cz.muni.fi.pa165.bluebat.facade.CustomerFacadeImpl;
import cz.muni.fi.pa165.bluebat.service.BeanMappingService;
import cz.muni.fi.pa165.bluebat.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Ondrej Vaca
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CustomerFacadeImplTest extends AbstractTestNGSpringContextTests {
    private static final Long CUSTOMER_ID = 3L;

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private CustomerService customerService;

    private CustomerFacade customerFacade;

    Customer customer;
    CustomerDTO customerDTO;
    CustomerCreateDTO customerCreateDTO;
    Customer insertedCustomer;
    List<Customer> customers;
    List<CustomerDTO> customerDTOs;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
        customerFacade = new CustomerFacadeImpl(customerService,beanMappingService);

        customer=getDefaultCustomer();
        insertedCustomer=getDefaultInsertedCustomer();
        customerDTO=getDefaultCustomerDTO();
        customerCreateDTO=getDefaultCustomerCreateDTO();
        customers=new ArrayList<>();
        customers.add(insertedCustomer);
        customerDTOs=new ArrayList<>();
        customerDTOs.add(customerDTO);

        when(beanMappingService.mapTo(customerCreateDTO, Customer.class)).thenReturn(customer);
        when(beanMappingService.mapTo(insertedCustomer, CustomerDTO.class)).thenReturn(customerDTO);
        when(beanMappingService.mapTo(customerDTO, Customer.class)).thenReturn(insertedCustomer);
        when(beanMappingService.mapTo(customers,CustomerDTO.class)).thenReturn(customerDTOs);

        doAnswer(invocation -> {
            ((Customer)invocation.getArgument(0)).setId(CUSTOMER_ID);
            return null;
        }).when(customerService).addCustomer(any());
    }

    @Test
    public void createCustomer_valid() {
        CustomerDTO result=customerFacade.createCustomer(customerCreateDTO);
        verify(customerService, times(1)).addCustomer(any());
        Assert.assertEquals(result,customerDTO);
    }

    @Test
    public void createCustomer_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerFacade.createCustomer(null));
    }

    @Test
    public void updateCustomer_valid() {
        CustomerDTO result=customerFacade.updateCustomer(customerDTO);

        verify(customerService, times(1)).updateCustomer(any());
        Assert.assertEquals(result,customerDTO);
    }

    @Test
    public void updateCustomer_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerFacade.updateCustomer(null));
    }

    @Test
    public void deleteCustomer_valid() {
        when(customerService.findCustomerById(CUSTOMER_ID)).thenReturn(customer);
        customerFacade.deleteCustomer(CUSTOMER_ID);

        verify(customerService, times(1)).deleteCustomer(customer);
    }

    @Test
    public void deleteCustomer_nullId_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerFacade.deleteCustomer(null));
    }

    @Test
    public void deleteCustomer_negative_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerFacade.deleteCustomer(-1L));
    }

    @Test
    public void getCustomerById_valid() {
        when(customerService.findCustomerById(CUSTOMER_ID)).thenReturn(insertedCustomer);

        CustomerDTO result = customerFacade.getCustomerById(CUSTOMER_ID);

        verify(customerService, times(1)).findCustomerById(CUSTOMER_ID);
        Assert.assertEquals(result,customerDTO);
    }

    @Test
    public void getCustomerById_nullId_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerFacade.getCustomerById(null));
    }

    @Test
    public void getCustomerById_negative_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerFacade.getCustomerById(-1L));
    }

    @Test
    public void getCustomerById_invalidId() {
        Long id = 1L;
        when(customerService.findCustomerById(any())).thenReturn(null);

        customerFacade.getCustomerById(1L);

        verify(customerService, times(1)).findCustomerById(id);
    }

    @Test
    public void getAllCustomers_valid() {
        when(customerService.findAllCustomers()).thenReturn(customers);

        List<CustomerDTO> result = customerFacade.getAllCustomers();

        verify(customerService, times(1)).findAllCustomers();
        Assert.assertEquals(result,customerDTOs);
    }

    private static Customer getDefaultCustomer() {
        Customer result = new Customer();

        result.setName("name");
        result.setSurname("surname");
        result.setBirthday(LocalDate.ofYearDay(1970, 1));
        result.setEmail("a@b.c");
        result.setAddress("address");
        result.setPhoneNumber(111111111L);

        return result;
    }

    private static CustomerDTO getDefaultCustomerDTO() {
        CustomerDTO result = new CustomerDTO();

        result.setId(CUSTOMER_ID);
        result.setName("name");
        result.setSurname("surname");
        result.setBirthday(LocalDate.ofYearDay(1970, 1));
        result.setEmail("a@b.c");
        result.setAddress("address");
        result.setPhoneNumber(111111111L);

        return result;
    }

    private static CustomerCreateDTO getDefaultCustomerCreateDTO() {
        CustomerCreateDTO result = new CustomerCreateDTO();

        result.setName("name");
        result.setSurname("surname");
        result.setBirthday(LocalDate.ofYearDay(1970, 1));
        result.setEmail("a@b.c");
        result.setAddress("address");
        result.setPhoneNumber(111111111L);

        return result;
    }

    private Customer getDefaultInsertedCustomer() {
        Customer customer = getDefaultCustomer();
        customer.setId(CUSTOMER_ID);
        return customer;
    }
}
