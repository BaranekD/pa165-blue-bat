package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.List;

@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class CustomerServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CustomerService customerService;

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Customer customer;
    private Customer notInsertedCustomer;

    private void setupCustomer() {
        customer = new Customer();
        customer.setName("Karel");
        customer.setSurname("Novák");
        customer.setAddress("Botanická 68A, 602 00 Brno");
        customer.setEmail("karel.novak@test.cz");
        customer.setPhoneNumber(123456789L);
        customer.setBirthday(LocalDate.of(1990, 3, 25));
        customerService.addCustomer(customer);
    }

    private void setupNotInsertedCustomer() {
        notInsertedCustomer = new Customer();
        notInsertedCustomer.setName("Tomáš");
        notInsertedCustomer.setSurname("Hampl");
        notInsertedCustomer.setAddress("Botanická 68A, 602 00 Brno");
        notInsertedCustomer.setEmail("karel.novak@test.cz");
        notInsertedCustomer.setPhoneNumber(123456789L);
        notInsertedCustomer.setBirthday(LocalDate.of(1990, 3, 25));
    }

    @Test
    public void _findAllCustomers_empty() {
        Assert.assertEquals(customerService.findAllCustomers().size(), 0);
    }

    @Test
    public void _findAllCustomers_oneInserted() {
        setupCustomer();
        List<Customer> customers = customerService.findAllCustomers();
        Assert.assertEquals(customers.size(), 1);
        Assert.assertEquals(customers.get(0), customer);
    }

    @Test
    public void addCustomer_null_InvalidDataAccessApiUsageException() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> customerService.addCustomer(null));
    }

    @Test
    public void addCustomer_valid() {
        setupCustomer();
        Customer found = customerService.findCustomerById(customer.getId());
        Assert.assertNotNull(found);
    }

    @Test
    public void updateCustomer_null_InvalidDataAccessApiUsageException() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> customerService.updateCustomer(null));
    }

    @Test
    public void updateCustomer_valid() {
        setupCustomer();
        Assertions.assertDoesNotThrow(() -> customerService.updateCustomer(customer));
    }

    @Test
    public void deleteCustomer_null_InvalidDataAccessApiUsageException() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> customerService.deleteCustomer(null));
    }

    @Test
    public void deleteCustomer_notInserted() {
        setupNotInsertedCustomer();
        Assertions.assertDoesNotThrow(() -> customerService.deleteCustomer(notInsertedCustomer));
    }

    @Test
    public void findCustomerById_notInserted() {
        Customer found = customerService.findCustomerById(2021L);
        Assert.assertNull(found);
    }

    @Test
    public void findCustomerById_valid() {
        setupCustomer();
        Customer found = customerService.findCustomerById(customer.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(found, customer);
    }

    @Test
    public void findCustomerById_removedCustomer() {
        setupCustomer();
        Assertions.assertDoesNotThrow(() -> customerService.deleteCustomer(customer));
        Customer found = customerService.findCustomerById(customer.getId());
        Assert.assertNull(found);
    }
}
