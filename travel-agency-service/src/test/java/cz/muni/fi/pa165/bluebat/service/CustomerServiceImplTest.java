package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dao.CustomerDao;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Ondrej Vaca
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CustomerServiceImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private CustomerDao customerDao;

    private CustomerService customerService;

    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);

        customerService = new CustomerServiceImpl(customerDao);

        doNothing().when(customerDao).create(any());
        doNothing().when(customerDao).update(any());
        doNothing().when(customerDao).delete(any());
    }

    @Test
    public void create_valid() {
        Customer customer = getDefaultCustomer();
        String password=getPassword();
        customerService.addCustomer(customer,password);
        verify(customerDao, times(1)).create(customer);
    }

    @Test
    public void create_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> customerService.addCustomer(null,null));
    }

    @Test
    public void create_DataAccessException() {
        Customer customer = getDefaultCustomer();
        String password=getPassword();
        doThrow(new IllegalArgumentException()).when(customerDao).create(any());

        Assertions.assertThrows(WrongDataAccessException.class, () -> customerService.addCustomer(customer,password));
    }

    @Test
    public void update_called() {
        Customer customer = getDefaultInsertedCustomer();
        Long id = customer.getId();
        when(customerDao.findById(id)).thenReturn(customer);

        customerService.updateCustomer(customer);

        verify(customerDao, times(1)).update(customer);
    }

    @Test
    public void update_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> customerService.updateCustomer(null));
    }

    @Test
    public void update_notInserted_IllegalArgumentException() {
        Customer customer = getDefaultInsertedCustomer();
        Long id = customer.getId();;
        when(customerDao.findById(id)).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(customer));
    }

    @Test
    public void update_daoThrowsException_WrongDataAccessException() {
        Customer customer = getDefaultCustomer();
        when(customerDao.findById(customer.getId())).thenReturn(customer);

        doThrow(new IllegalArgumentException()).when(customerDao).update(customer);
        Assertions.assertThrows(WrongDataAccessException.class, () -> customerService.updateCustomer(customer));
    }

    @Test
    public void delete_called() {
        Customer customer = getDefaultInsertedCustomer();
        when(customerDao.findById(customer.getId())).thenReturn(customer);

        customerService.deleteCustomer(customer);
        verify(customerDao, times(1)).delete(customer);
    }

    @Test
    public void delete_daoThrowsException_WrongDataAccessException() {
        Customer customer = getDefaultCustomer();
        when(customerDao.findById(customer.getId())).thenReturn(customer);

        doThrow(new IllegalArgumentException()).when(customerDao).delete(customer);
        Assertions.assertThrows(WrongDataAccessException.class, () -> customerService.deleteCustomer(customer));
    }

    @Test
    public void findCustomerById_notInserted_valid() {
        Customer first = getDefaultInsertedCustomer();
        Customer second = getDefaultInsertedCustomer();
        Long id = first.getId();
        when(customerService.findCustomerById(id)).thenReturn(first);

        Customer found = customerService.findCustomerById(1L);

        Assert.assertEquals(found, second);
    }

    @Test
    public void findCustomerById_notInserted_null() {
        when(customerService.findCustomerById(1L)).thenReturn(null);

        Customer found = customerService.findCustomerById(1L);

        Assert.assertNull(found);
    }

    @Test
    public void findCustomerById_daoThrows() {
        when(customerDao.findById(any())).thenThrow(IllegalArgumentException.class);

        Assertions.assertThrows(WrongDataAccessException.class, ()-> customerService.findCustomerById(1L));
    }

     @Test
    public void findAllCustomers_valid() {
        Customer first = getDefaultInsertedCustomer();
        Customer second = getDefaultInsertedCustomer();
        List<Customer> customers=new ArrayList<>();
        customers.add(first);
        customers.add(second);
        when(customerService.findAllCustomers()).thenReturn(customers);

        List<Customer> found = customerService.findAllCustomers();

        Assert.assertEquals(found, customers);
    }

    @Test
    public void findAllCustomers_null() {
        when(customerService.findAllCustomers()).thenReturn(null);

        List<Customer> found = customerService.findAllCustomers();

        Assert.assertNull(found);
    }

    @Test
    public void findAllCustomers_daoThrows() {
        when(customerService.findAllCustomers()).thenThrow(IllegalArgumentException.class);

        Assertions.assertThrows(WrongDataAccessException.class, ()-> customerService.findAllCustomers());
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

    private static Customer getDefaultInsertedCustomer() {
        Customer result = getDefaultCustomer();
        result.setId(1L);
        return result;
    }

    private static String getPassword(){
        return (new String("aa123"));
    }

}
