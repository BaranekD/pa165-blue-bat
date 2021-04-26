package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Tomáš Hampl on 18.4.21.
 */
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CustomerDao customerDao;

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Customer customer;
    private Customer secondCustomer;
    private Customer notInsertedCustomer;

    @BeforeMethod
    public void setup(){
        setupCustomer();
        setupSecondCustomer();
        setupNotInsertedCustomer();
    }

    @Test
    public void create_null_InvalidDataAccessApiUsageException() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> customerDao.create(null));
    }

    @Test
    public void create_valid() {
        Assertions.assertDoesNotThrow(() -> customerDao.create(notInsertedCustomer));
        Assert.assertTrue(notInsertedCustomer.getId() > 0L);

        Customer found = customerDao.findById(notInsertedCustomer.getId());

        Assert.assertNotNull(found);
        Assert.assertEquals(found, notInsertedCustomer);
    }

    @Test
    public void update_null_InvalidDataAccessApiUsageException() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> customerDao.update(null));
    }

    @Test
    public void update_valid() {
        customer.setSurname("Smith");
        Assertions.assertDoesNotThrow(() -> customerDao.update(customer));

        Customer found = customerDao.findById(customer.getId());
        Assert.assertEquals(found.getSurname(), "Smith");
    }

    @Test
    public void delete_null_InvalidDataAccessApiUsageException() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> customerDao.delete(null));
    }

    @Test
    public void delete_notInserted() {
        Assertions.assertDoesNotThrow(() -> customerDao.delete(notInsertedCustomer));
    }

    @Test
    public void delete_valid() {
        Assertions.assertDoesNotThrow(() -> customerDao.delete(customer));

        Customer found = customerDao.findById(customer.getId());
        Assert.assertNull(found);
    }

    @Test
    public void findById_notInserted() {
        Customer found = customerDao.findById(2021L);
        Assert.assertNull(found);
    }

    @Test
    public void findById_valid() {
        Customer found = customerDao.findById(customer.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(found, customer);
    }

    @Test
    public void findById_removedCustomer() {
        removeCustomer(customer);

        Customer found = customerDao.findById(customer.getId());
        Assert.assertNull(found);
    }

    @Test
    public void findAll_doesNotThrow() {
        Assertions.assertDoesNotThrow(() -> customerDao.findAll());
    }

    @Test
    public void findAll_oneInserted() {
        List<Customer> customers = customerDao.findAll();
        int size = customers.size();

        insertCustomer(secondCustomer);

        customers = customerDao.findAll();
        Assert.assertEquals(customers.size(), size + 1);
        Assert.assertTrue(customers.stream().anyMatch(c -> c.equals(secondCustomer)));
    }

    @Test
    public void findAll_oneDeleted() {
        setupSecondCustomer();
        List<Customer> customers = customerDao.findAll();
        int size = customers.size();

        removeCustomer(customer);

        customers = customerDao.findAll();
        Assert.assertEquals(customers.size(), size - 1);
        Assert.assertFalse(customers.stream().anyMatch(c -> c.equals(customer)));
    }

    private void setupCustomer() {
        customer = new Customer();
        customer.setName("Karel");
        customer.setSurname("Novák");
        customer.setAddress("Botanická 68A, 602 00 Brno");
        customer.setEmail("karel.novak@test.cz");
        customer.setPhoneNumber(123456789L);
        customer.setBirthday(LocalDate.of(1990, 3, 25));

        insertCustomer(customer);
    }

    private void setupSecondCustomer() {
        secondCustomer = new Customer();
        secondCustomer.setName("Tomáš");
        secondCustomer.setSurname("Hampl");
        secondCustomer.setAddress("Šumavská 120, Brno 602 00");
        secondCustomer.setEmail("hampl@ics.muni.cz");
        secondCustomer.setPhoneNumber(123456789L);
        secondCustomer.setBirthday(LocalDate.of(1990, 3, 25));
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

    private void insertCustomer(Customer customer) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }

    private void removeCustomer(Customer ...customers) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            for (Customer customer: customers) {
                em.remove(em.contains(customer) ? customer : em.merge(customer));
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }
}
