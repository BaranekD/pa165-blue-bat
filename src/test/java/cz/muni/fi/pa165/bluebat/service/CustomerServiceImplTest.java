package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class CustomerServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CustomerService customerService;

    @Test
    public void create_valid() {
        Customer customer = new Customer();
        customerService.addCustomer(customer);
        Assert.assertNotEquals(customer.getId(), 0L);

        Customer found = customerService.findCustomerById(customer.getId());
        Assert.assertEquals(found, customer);
    }

    @Test
    public void findAll_empty() {
        List<Customer> customers = customerService.findAllCustomers();
        Assert.assertEquals(customers.size(), 0);
    }

    @Test
    public void findById_notExisting() {
        Customer customer = customerService.findCustomerById(1L);
        Assert.assertNull(customer);
    }
}
