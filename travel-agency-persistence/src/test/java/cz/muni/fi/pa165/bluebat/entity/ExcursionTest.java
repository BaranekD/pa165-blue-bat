package cz.muni.fi.pa165.bluebat.entity;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import java.time.Duration;
import java.time.LocalDate;

/**
 * @author Dominik Baranek <460705@mail.muni.cz>
 */
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ExcursionTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private void persistExcursion(Excursion excursion) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(excursion);
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }

    private Excursion getFullyInitializedExcursion() {
        Trip trip = new Trip();
        trip.setAvailableTrips(5);
        trip.setDateFrom(LocalDate.of(2022,6,5));
        trip.setDestination("Brno");
        trip.setDateTo(LocalDate.of(2022,3,15));
        trip.setName("Test trip");

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(trip);
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }

        Excursion result = new Excursion();
        result.setName("testName");
        result.setDuration(Duration.ofDays(1));
        result.setDateFrom(LocalDate.now());
        result.setDestination("testDestination");
        result.setDescription("testDescription");
        result.setTrip(trip);

        return result;
    }

    @DataProvider(name = "valuesNotBlank")
    private static Object[][] getInvalidValues() {
        return new String[][] { { null }, { "" }, { " " }, { "\t" }, { "\n" } };
    }

    @Test
    public void excursion_fullyInitialized() {
        Excursion excursion = getFullyInitializedExcursion();
        Assertions.assertDoesNotThrow(() -> persistExcursion(excursion));
    }

    @Test
    public void excursionName_null() {
        Excursion excursion = getFullyInitializedExcursion();
        excursion.setName(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> persistExcursion(excursion));
    }

    @Test
    public void excursionDuration_null() {
        Excursion excursion = getFullyInitializedExcursion();
        excursion.setDuration(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> persistExcursion(excursion));
    }

    @Test
    public void excursionDateFrom_null() {
        Excursion excursion = getFullyInitializedExcursion();
        excursion.setDateFrom(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> persistExcursion(excursion));
    }

    @Test
    public void excursionDestination_null() {
        Excursion excursion = getFullyInitializedExcursion();
        excursion.setDestination(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> persistExcursion(excursion));
    }

    @Test(dataProvider = "valuesNotBlank")
    public void excursionName_invalid(String name) {
        Excursion excursion = getFullyInitializedExcursion();
        excursion.setName(name);

        Assertions.assertThrows(ConstraintViolationException.class, () -> persistExcursion(excursion));
    }

    @Test(dataProvider = "valuesNotBlank")
    public void excursionDestination_invalid(String destination) {
        Excursion excursion = getFullyInitializedExcursion();
        excursion.setDestination(destination);

        Assertions.assertThrows(ConstraintViolationException.class, () -> persistExcursion(excursion));
    }
}
