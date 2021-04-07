package cz.muni.fi.pa165.bluebat.entity;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Dominik Baranek <460705@mail.muni.cz>
 */
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class ExcursionTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void nullDateFromThrowsPersistenceException() {
        assertThrows(PersistenceException.class, () -> {
            Excursion e = createTestingExcursion();
            e.setDateFrom(null);

            EntityManager em = null;
            try {
                em = emf.createEntityManager();
                em.getTransaction().begin();
                em.persist(e);
                em.getTransaction().commit();
            } finally {
                if (em != null) em.close();
            }
        });
    }

    @Test
    public void nullDurationThrowsPersistenceException() {
        assertThrows(PersistenceException.class, () -> {
            Excursion e = createTestingExcursion();
            e.setDuration(null);

            EntityManager em = null;
            try {
                em = emf.createEntityManager();
                em.getTransaction().begin();
                em.persist(e);
                em.getTransaction().commit();
            } finally {
                if (em != null) em.close();
            }
        });
    }

    @Test
    public void nullDestinationThrowsPersistenceException() {
        assertThrows(PersistenceException.class, () -> {
            Excursion e = createTestingExcursion();
            e.setDestination(null);

            EntityManager em = null;
            try {
                em = emf.createEntityManager();
                em.getTransaction().begin();
                em.persist(e);
                em.getTransaction().commit();
            } finally {
                if (em != null) em.close();
            }
        });
    }

    @Test
    public void nullNameThrowsPersistenceException() {
        assertThrows(PersistenceException.class, () -> {
            Excursion e = createTestingExcursion();
            e.setName(null);

            EntityManager em = null;
            try {
                em = emf.createEntityManager();
                em.getTransaction().begin();
                em.persist(e);
                em.getTransaction().commit();
            } finally {
                if (em != null) em.close();
            }
        });
    }


    private Excursion createTestingExcursion() {
        Excursion e = new Excursion();

        e.setDateFrom(LocalDate.now());
        e.setName("testName");
        e.setDuration(Duration.ZERO);
        e.setDestination("testDestination");

        return e;
    }
}
