package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;

import javax.persistence.EntityExistsException;
import java.util.List;


@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class CustomerDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CustomerDao customerDao;

    private Customer customer;
    private Customer notInsertedCustomer;

    private void setupCustomer() {
        customer = new Customer();
        customer.setName("Karel");
        customer.setSurname("Novák");
        customerDao.create(customer);
    }

    private void setupNotInsertedCustomer() {
        notInsertedCustomer = new Customer();
        notInsertedCustomer.setName("Tomáš");
        notInsertedCustomer.setSurname("Hampl");
    }


    @Test
    public void create_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerDao.create(null));
    }

    @Test
    @Rollback
    public void create_valid() {
        Assertions.assertDoesNotThrow(() -> customerDao.create(new Customer()));
    }

    @Test
    @Rollback
    public void createCustomerTwice_EntityExistsException() {
        setupCustomer();
        Assertions.assertThrows(EntityExistsException.class, () -> customerDao.create(customer));
    }

    @Test
    public void updateNull_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerDao.update(null));
    }

    @Test
    public void updateNotInsertedCustomer_IllegalArgumentException() {
        setupNotInsertedCustomer();
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerDao.update(notInsertedCustomer));
    }

    @Test
    @Rollback
    public void update_valid() {
        setupCustomer();
        Assertions.assertDoesNotThrow(() -> customerDao.create(customer));
    }

    @Test
    public void deleteNull_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerDao.delete(null));
    }


    @Test
    public void deleteNotInsertedCustomer_IllegalArgumentException() {
        setupNotInsertedCustomer();
        Assertions.assertThrows(IllegalArgumentException.class, () -> customerDao.update(notInsertedCustomer));
    }

    @Test
    @Rollback
    public void delete_valid() {
        setupCustomer();
        Assertions.assertDoesNotThrow(() -> customerDao.delete(customer));
    }

    @Test
    public void findById_notInsertedCustomer() {
        setupNotInsertedCustomer();
        Customer found = customerDao.findById(notInsertedCustomer.getId());
        Assert.assertNull(found);
    }

    @Test
    @Rollback
    public void findById_valid() {
        setupCustomer();
        Customer found = customerDao.findById(customer.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(found, customer);
    }

    @Test
    @Rollback
    public void findById_removedCustomer() {
        setupCustomer();
        customerDao.delete(customer);
        Customer found = customerDao.findById(customer.getId());
        Assert.assertNull(found);
    }

    @Test
    public void findAll_Empty() {
        Assert.assertEquals(customerDao.findAll().size(), 0);
    }

    @Test
    @Rollback
    public void findAll_OneInserted() {
        setupCustomer();
        List<Customer> customers = customerDao.findAll();
        Assert.assertEquals(customers.size(), 1);
        Assert.assertEquals(customers.get(0), customer);
    }
}
