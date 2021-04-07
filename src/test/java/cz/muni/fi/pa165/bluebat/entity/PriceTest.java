package cz.muni.fi.pa165.bluebat.entity;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Dominik Baranek <460705@mail.muni.cz>
 */
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class PriceTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void nullValidFromThrowsPersistenceException() {
        assertThrows(PersistenceException.class, () -> {
            Price p = createTestingPrice();
            p.setValidFrom(null);

            EntityManager em = null;
            try {
                em = emf.createEntityManager();
                em.getTransaction().begin();
                em.persist(p);
                em.getTransaction().commit();
            } finally {
                if (em != null) em.close();
            }
        });
    }

    @Test
    public void nullAmountThrowsPersistenceException() {
        assertThrows(PersistenceException.class, () -> {
            Price p = createTestingPrice();
            p.setAmount(null);

            EntityManager em = null;
            try {
                em = emf.createEntityManager();
                em.getTransaction().begin();
                em.persist(p);
                em.getTransaction().commit();
            } finally {
                if (em != null) em.close();
            }
        });
    }

    private Price createTestingPrice() {
        Price p = new Price();
        p.setValidFrom(LocalDate.now());
        p.setAmount(BigDecimal.ZERO);

        return p;
    }
}
