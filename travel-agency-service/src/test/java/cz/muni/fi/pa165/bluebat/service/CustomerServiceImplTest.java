package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dao.CustomerDao;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
        customerService.addCustomer(customer);
        verify(customerDao, times(1)).create(customer);
    }

    @Test
    public void create_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> customerService.addCustomer(null));
    }

    @Test
    public void create_daoThrowsException_WrongDataAccessException() {
        Customer customer = getDefaultCustomer();
        doThrow(new IllegalArgumentException()).when(customerDao).create(customer);

        Assertions.assertThrows(WrongDataAccessException.class, () -> customerService.addCustomer(customer));
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

        doThrow(new IllegalArgumentException()).when(customerDao).update(customer);
        Assertions.assertThrows(WrongDataAccessException.class, () -> customerService.updateCustomer(customer));
    }

    @Test
    public void delete_called() {
        Customer customer = getDefaultInsertedCustomer();

        customerService.deleteCustomer(customer);
        verify(customerDao, times(1)).delete(customer);
    }

    @Test
    public void delete_daoThrowsException_WrongDataAccessException() {
        Customer customer = getDefaultCustomer();

        doThrow(new IllegalArgumentException()).when(customerDao).delete(customer);
        Assertions.assertThrows(WrongDataAccessException.class, () -> customerService.deleteCustomer(customer));
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
}
