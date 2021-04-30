package cz.muni.fi.pa165.bluebat.service.facade;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dto.CustomerCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.CustomerDTO;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.facade.CustomerFacade;
import cz.muni.fi.pa165.bluebat.service.BeanMappingService;
import cz.muni.fi.pa165.bluebat.service.CustomerService;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Ondrej Vaca
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CustomerFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanMappingService beanMappingService;

    @Mock
    private CustomerService customerService;

    private CustomerFacade customerFacade;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.openMocks(this);
        customerFacade = new CustomerFacadeImpl(customerService,beanMappingService);
    }

    @Test
    public void createCustomer_valid() {
        Customer customer = getDefaultCustomer();
        CustomerCreateDTO customerCreateDTO =getDefaultCustomerCreateDTO();

        customerFacade.createCustomer(customerCreateDTO);

        verify(customerService, times(1)).addCustomer(customer);
    }

    @Test
    public void updateCustomer_valid() {
        Long id = 1L;
        Customer customer = getDefaultCustomer();
        customer.setId(id);
        CustomerDTO customerDTO = getDefaultCustomerDTO();
        customerDTO.setId(id);

        customerFacade.updateCustomer(customerDTO);

        verify(customerService, times(1)).updateCustomer(customer);
    }

    @Test
    public void deleteCustomer_valid() {
        Long id = 1L;
        Customer customer = getDefaultCustomer();
        customer.setId(id);
        when(customerService.findCustomerById(id)).thenReturn(customer);

        customerFacade.deleteCustomer(id);

        verify(customerService, times(1)).deleteCustomer(customer);
    }

    @Test
    public void getCustomerById_valid() {
        Long id = 1L;
        Customer customer = getDefaultCustomer();
        customer.setId(id);
        CustomerDTO customerDTO = getDefaultCustomerDTO();
        customerDTO.setId(id);
        when(customerService.findCustomerById(id)).thenReturn(customer);

        CustomerDTO result = customerFacade.getCustomerById(id);

        verify(customerService, times(1)).findCustomerById(id);
        Assert.assertEquals(result.getId(), customerDTO.getId());
        Assert.assertEquals(result.getAddress(), customerDTO.getAddress());
        Assert.assertEquals(result.getName(), customerDTO.getName());
        Assert.assertEquals(result.getBirthday(), customerDTO.getBirthday());
        Assert.assertEquals(result.getEmail(), customerDTO.getEmail());
        Assert.assertEquals(result.getSurname(), customerDTO.getSurname());
        Assert.assertEquals(result.getPhoneNumber(), customerDTO.getPhoneNumber());
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
        Long idFirst= 1L;
        Long idSecond= 2L;
        Customer first = getDefaultCustomer();
        Customer second=getDefaultCustomer();
        first.setId(idFirst);
        second.setId(idSecond);
        List<Customer>customers = new ArrayList<>();
        customers.add(0,first);
        customers.add(1,second);

        CustomerDTO customerDTOFirst=getDefaultCustomerDTO();
        CustomerDTO customerDTOSecond=getDefaultCustomerDTO();
        customerDTOFirst.setId(idFirst);
        customerDTOSecond.setId(idSecond);
        List<CustomerDTO>customersDTO = new ArrayList<>();
        customersDTO.add(0,customerDTOFirst);
        customersDTO.add(1,customerDTOSecond);

        when(customerService.findAllCustomers()).thenReturn(customers);

        List<CustomerDTO> result = customerFacade.getAllCustomers();

        verify(customerService, times(1)).findAllCustomers();
        for(int i=0;i<2;i++) {
            Assert.assertEquals(result.get(i).getId(), customersDTO.get(i).getId());
            Assert.assertEquals(result.get(i).getAddress(), customersDTO.get(i).getAddress());
            Assert.assertEquals(result.get(i).getName(), customersDTO.get(i).getName());
            Assert.assertEquals(result.get(i).getBirthday(), customersDTO.get(i).getBirthday());
            Assert.assertEquals(result.get(i).getEmail(), customersDTO.get(i).getEmail());
            Assert.assertEquals(result.get(i).getSurname(), customersDTO.get(i).getSurname());
            Assert.assertEquals(result.get(i).getPhoneNumber(), customersDTO.get(i).getPhoneNumber());
        }
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
}
