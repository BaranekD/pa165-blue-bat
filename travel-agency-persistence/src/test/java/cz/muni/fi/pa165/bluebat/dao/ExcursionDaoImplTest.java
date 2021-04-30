package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.Duration;
import java.time.LocalDate;

/**
 * @author Dominik Baranek <460705@mail.muni.cz/>
 */
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ExcursionDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ExcursionDao excursionDao;

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Trip trip;
    private Excursion excursion;
    private Excursion notInsertedExcursion;

    @BeforeMethod
    public void setup(){
        setupTrip();
        setupExcursion();
        setupNotInsertedExcursion();
    }

    @Test
    public void create_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> excursionDao.create(null));
    }

    @Test
    public void create_valid() {
        Assertions.assertDoesNotThrow(() -> excursionDao.create(notInsertedExcursion));
        Assert.assertTrue(notInsertedExcursion.getId() > 0L);

        Excursion found = excursionDao.findById(notInsertedExcursion.getId());

        Assert.assertNotNull(found);
        Assert.assertEquals(found, notInsertedExcursion);
    }

    @Test
    public void update_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> excursionDao.update(null));
    }

    @Test
    public void update_valid() {
        excursion.setName("updated");
        Assertions.assertDoesNotThrow(() -> excursionDao.update(excursion));
        Assertions.assertEquals("updated", excursionDao.findById(excursion.getId()).getName());
    }

    @Test
    public void delete_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> excursionDao.delete(null));
    }

    @Test
    public void delete_notInserted() {
        Assertions.assertDoesNotThrow(() -> excursionDao.delete(notInsertedExcursion));
    }

    @Test
    public void delete_valid() {
        excursionDao.delete(excursion);

        Excursion found = excursionDao.findById(excursion.getId());
        Assert.assertNull(found);
    }

    @Test
    public void findById_notInserted() {
        Excursion found = excursionDao.findById(2024L);
        Assert.assertNull(found);
    }

    @Test
    public void findById_valid() {
        Excursion found = excursionDao.findById(excursion.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(found, excursion);
    }

    @Test
    public void findById_removedExcursion() {
        removeExcursion(excursion);

        Excursion found = excursionDao.findById(excursion.getId());
        Assert.assertNull(found);
    }

    private void setupTrip() {
        trip = new Trip();
        trip.setAvailableTrips(5);
        trip.setDateFrom(LocalDate.now().plusDays(7L));
        trip.setDestination("Brno");
        trip.setDateTo(LocalDate.now().plusDays(14L));
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
    }

    private void setupExcursion() {
        excursion = new Excursion();
        excursion.setName("testName");
        excursion.setDuration(Duration.ofDays(1));
        excursion.setDateFrom(LocalDate.now());
        excursion.setDestination("testDestination");
        excursion.setDescription("testDescription");
        excursion.setTrip(trip);

        insertExcursion(excursion);
    }

    private void setupNotInsertedExcursion() {
        notInsertedExcursion = new Excursion();
        notInsertedExcursion.setName("notInsertedName");
        notInsertedExcursion.setDuration(Duration.ofDays(5));
        notInsertedExcursion.setDateFrom(LocalDate.now());
        notInsertedExcursion.setDestination("notInsertedDestination");
        notInsertedExcursion.setDescription("notInsertedDescription");
        notInsertedExcursion.setTrip(trip);
    }

    private void insertExcursion(Excursion excursion) {
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

    private void removeExcursion(Excursion excursion) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.contains(excursion) ? excursion : em.merge(excursion));
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }
}
