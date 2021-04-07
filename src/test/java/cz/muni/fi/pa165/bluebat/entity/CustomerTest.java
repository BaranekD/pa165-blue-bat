package cz.muni.fi.pa165.bluebat.entity;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.RollbackException;
import java.time.LocalDate;


@SpringBootTest(classes = PersistenceTravelAgencyApplicationContext.class)
public class CustomerTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private void persisCustomer(Customer customer, String assertMessage) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
        } catch (RollbackException ex) {
            return;
        }
        finally {
            if (em != null) em.close();
        }

        Assertions.fail(assertMessage);
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
        persisCustomer(customer, "Entity is allowed to persis with Name that is null");
    }

    @Test
    public void customerSurname_null() {
        Customer customer = getFullyInitializedCustomer();
        customer.setSurname(null);
        persisCustomer(customer, "Entity is allowed to persis with Surname that is null");
    }

    @Test
    public void customerAddress_null() {
        Customer customer = getFullyInitializedCustomer();
        customer.setAddress(null);
        persisCustomer(customer, "Entity is allowed to persis with Address that is null");
    }

    @Test
    public void customerEmail_null() {
        Customer customer = getFullyInitializedCustomer();
        customer.setEmail(null);
        persisCustomer(customer, "Entity is allowed to persis with Email that is null");
    }

    @Test
    public void customerPhoneNumber_null() {
        Customer customer = getFullyInitializedCustomer();
        customer.setPhoneNumber(null);
        persisCustomer(customer, "Entity is allowed to persis with PhoneNumber that is null");
    }

    @Test
    public void customerBirthday_null() {
        Customer customer = getFullyInitializedCustomer();
        customer.setBirthday(null);
        persisCustomer(customer, "Entity is allowed to persis with Birthday that is null");
    }
}
