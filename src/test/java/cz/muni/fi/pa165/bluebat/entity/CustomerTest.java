package cz.muni.fi.pa165.bluebat.entity;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;

import org.junit.jupiter.api.Assertions;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;


@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class CustomerTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private void persistCustomer(Customer customer) {
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

    private void deleteCustomer(Customer customer) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.contains(customer) ? customer : em.merge(customer));
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

    @DataProvider(name = "valuesNotBlank")
    private static Object[][] getInvalidValues() {
        return new String[][] { { null }, { "" }, { " " }, { "\t" }, { "\n" } };
    }

    @DataProvider(name = "valuesNotEmail")
    private static Object[][] getInvalidEmailValues() {
        return new String[][] { { "test" }, { "test@" }, { "@test.com" }, { "a@b@test.com" }, { "abc@test..com" },
                { "abc..def@test.com" }, { ".abc@test.com" }, { "abc@-test.com" }, { "abc.@test.com" },
                { "a\"b\"c@test.com" } };
    }

    @Test
    public void customer_fullyInitialized() {
        Customer customer = getFullyInitializedCustomer();
        Assertions.assertDoesNotThrow(() -> persistCustomer(customer));
        deleteCustomer(customer);
    }

    @Test(dataProvider = "valuesNotBlank")
    public void customerName_invalid(String name) {
        Customer customer = getFullyInitializedCustomer();
        customer.setName(name);
        Assertions.assertThrows(PersistenceException.class, () -> persistCustomer(customer));
    }

    @Test(dataProvider = "valuesNotBlank")
    public void customerSurname_invalid(String surname) {
        Customer customer = getFullyInitializedCustomer();
        customer.setSurname(surname);
        Assertions.assertThrows(PersistenceException.class, () -> persistCustomer(customer));
    }

    @Test(dataProvider = "valuesNotBlank")
    public void customerAddress_invalid(String address) {
        Customer customer = getFullyInitializedCustomer();
        customer.setAddress(address);
        Assertions.assertThrows(PersistenceException.class, () -> persistCustomer(customer));
    }

    @Test(dataProvider = "valuesNotBlank")
    public void customerEmail_invalid_blank(String email) {
        Customer customer = getFullyInitializedCustomer();
        customer.setEmail(email);
        Assertions.assertThrows(PersistenceException.class, () -> persistCustomer(customer));
    }

    @Test(dataProvider = "valuesNotEmail")
    public void customerEmail_invalid_notEmail(String email) {
        Customer customer = getFullyInitializedCustomer();
        customer.setEmail(email);
        Assertions.assertThrows(PersistenceException.class, () -> persistCustomer(customer));
    }

    @Test
    public void customerPhoneNumber_null() {
        Customer customer = getFullyInitializedCustomer();
        customer.setPhoneNumber(null);
        Assertions.assertThrows(PersistenceException.class, () -> persistCustomer(customer));
    }

    @Test
    public void customerPhoneNumber_negativeValue() {
        Customer customer = getFullyInitializedCustomer();
        customer.setPhoneNumber(-15L);
        Assertions.assertThrows(PersistenceException.class, () -> persistCustomer(customer));
    }

    @Test
    public void customerBirthday_null() {
        Customer customer = getFullyInitializedCustomer();
        customer.setBirthday(null);
        Assertions.assertThrows(PersistenceException.class, () -> persistCustomer(customer));
    }

    @Test
    public void customerBirthday_inFuture() {
        Customer customer = getFullyInitializedCustomer();
        customer.setBirthday(LocalDate.now().plusDays(1L));
        Assertions.assertThrows(PersistenceException.class, () -> persistCustomer(customer));
    }
}
