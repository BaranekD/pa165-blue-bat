package cz.muni.fi.pa165.bluebat.entity;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;

import org.junit.jupiter.api.Assertions;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.persistence.RollbackException;
import java.time.LocalDate;


@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class CustomerTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private void persisCustomer(Customer customer) {
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

    private Customer getFullyInitializedCustomer() {
        Customer result = new Customer();

        result.setName("Karel");
        result.setSurname("Novák");
        result.setAddress("Botanická 68A, 602 00 Brno");
        result.setEmail("karel.novak@test.cz");
        result.setPhoneNumber(123456789L);
        result.setBirthday(LocalDate.of(1990, 3, 25));

        return result;
    }

    @Test
    public void customer_fullyInitialized() {
        Customer customer = getFullyInitializedCustomer();
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
        } catch (RollbackException ex) {
            Assert.fail("Persist of fully initialized customer failed");
        }
        finally {
            if (em != null) em.close();
        }
    }

    @Test
    public void customerName_null() {
        Customer customer = getFullyInitializedCustomer();
        customer.setName(null);
        Assertions.assertThrows(PersistenceException.class, () -> persisCustomer(customer));
    }

    @Test
    public void customerSurname_null() {
        Customer customer = getFullyInitializedCustomer();
        customer.setSurname(null);
        Assertions.assertThrows(PersistenceException.class, () -> persisCustomer(customer));
    }

    @Test
    public void customerAddress_null() {
        Customer customer = getFullyInitializedCustomer();
        customer.setAddress(null);
        Assertions.assertThrows(PersistenceException.class, () -> persisCustomer(customer));
    }

    @Test
    public void customerEmail_null() {
        Customer customer = getFullyInitializedCustomer();
        customer.setEmail(null);
        Assertions.assertThrows(PersistenceException.class, () -> persisCustomer(customer));
    }

    @Test
    public void customerPhoneNumber_null() {
        Customer customer = getFullyInitializedCustomer();
        customer.setPhoneNumber(null);
        Assertions.assertThrows(PersistenceException.class, () -> persisCustomer(customer));
    }

    @Test
    public void customerBirthday_null() {
        Customer customer = getFullyInitializedCustomer();
        customer.setBirthday(null);
        Assertions.assertThrows(PersistenceException.class, () -> persisCustomer(customer));
    }
}
