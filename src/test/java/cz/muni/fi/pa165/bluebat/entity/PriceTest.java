package cz.muni.fi.pa165.bluebat.entity;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Dominik Baranek <460705@mail.muni.cz>
 */
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PriceTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private void persistPrice(Price price) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(price);
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }

    private Price getFullyInitializedPrice() {
        Price result = new Price();
        result.setAmount(BigDecimal.TEN);
        result.setValidFrom(LocalDate.now());

        return result;
    }

    @Test
    public void price_fullyInitialized() {
        Price price = getFullyInitializedPrice();
        Assertions.assertDoesNotThrow(() -> persistPrice(price));
    }

    @Test
    public void priceAmount_null() {
        Price price = getFullyInitializedPrice();
        price.setAmount(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> persistPrice(price));
    }

    @Test
    public void priceValidFrom_null() {
        Price price = getFullyInitializedPrice();
        price.setValidFrom(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> persistPrice(price));
    }

    @Test
    public void priceAmount_negativeValue() {
        Price price = getFullyInitializedPrice();
        price.setAmount(BigDecimal.valueOf(-1));
        Assertions.assertThrows(ConstraintViolationException.class, () -> persistPrice(price));
    }
}
