package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Reservation;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Ondrej Vaca
 */
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ReservationDao reservationDao;

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Reservation reservation;

    private Reservation notInsertedReservation;

    private Customer customer;

    private Customer secondCustomer;

    private Customer notInsertedCustomer;

    private Trip trip;

    private Price price;

    @BeforeMethod
    public void setup(){
        setupCustomer();
        setupSecondCustomer();
        setupNotInsertedCustomer();

        setupPrice();
        setupTrip();

        setupReservation();
        setupNotInsertedReservation();
    }

    @Test
    public void create_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationDao.create(null));
    }

    @Test
    public void create_valid() {
        Assertions.assertDoesNotThrow(() -> reservationDao.create(notInsertedReservation));
        Assert.assertTrue(notInsertedReservation.getId() > 0L);

        Reservation found = reservationDao.findById(notInsertedReservation.getId());

        Assert.assertNotNull(found);
        Assert.assertEquals(found, notInsertedReservation);
    }

    @Test
    public void update_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationDao.update(null));
    }

    @Test
    public void update_valid() {
        reservation.setCustomer(secondCustomer);
        Assertions.assertDoesNotThrow(() -> reservationDao.update(reservation));

        Reservation found = reservationDao.findById(reservation.getId());
        Assert.assertEquals(found.getCustomer().getSurname(), "Zeman");
    }

    @Test
    public void delete_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationDao.delete(null));
    }

    @Test
    public void delete_valid() {
        Assertions.assertDoesNotThrow(() -> reservationDao.delete(reservation));
        Reservation found = reservationDao.findById(reservation.getId());
        Assert.assertNull(found);
    }

    @Test
    public void findById_notInserted() {
        Reservation found = reservationDao.findById(2021L);
        Assert.assertNull(found);
    }

    @Test
    public void findById_valid() {
        Reservation found = reservationDao.findById(reservation.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(found, reservation);
    }

    @Test
    public void findById_removedReservation() {
        removeReservation(reservation);

        Reservation found = reservationDao.findById(reservation.getId());
        Assert.assertNull(found);
    }

    @Test
    public void findAll_doesNotThrow() {
        Assertions.assertDoesNotThrow(() -> reservationDao.findAll());
    }

    @Test
    public void findAll_oneInserted() {
        List<Reservation> reservations = reservationDao.findAll();
        int size = reservations.size();

        insertReservation(notInsertedReservation);

        reservations = reservationDao.findAll();
        Assert.assertEquals(reservations.size(), size + 1);
        Assert.assertTrue(reservations.stream().anyMatch(r -> r.equals(notInsertedReservation)));
    }

    @Test
    public void findAll_oneDeleted() {
        setupNotInsertedReservation();
        List<Reservation> reservations = reservationDao.findAll();
        int size = reservations.size();

        removeReservation(reservation);

        reservations = reservationDao.findAll();
        Assert.assertEquals(reservations.size(), size - 1);
        Assert.assertFalse(reservations.stream().anyMatch(r -> r.equals(customer)));
    }

    private void setupTrip() {
        trip = new Trip();
        trip.setName("Trip to Slovakia");
        trip.setDateTo(LocalDate.now().plusDays(2));
        trip.setDateFrom(LocalDate.now().plusDays(1));
        trip.setDestination("Slovakia");
        trip.setAvailableTrips(5);

        insertTrip(trip);
    }

    private void setupCustomer() {
        customer = new Customer();
        customer.setName("Karel");
        customer.setSurname("Novák");
        customer.setAddress("Botanická 68A, 602 00 Brno");
        customer.setEmail("karel.novak@test.cz");
        customer.setPhoneNumber(123456789L);
        customer.setBirthday(LocalDate.of(1990, 3, 25));
        customer.setPasswordHash("ab123");
        customer.setNickName("ab.ba");
        customer.setAdmin(true);

        insertCustomer(customer);
    }

    private void setupNotInsertedCustomer() {
        notInsertedCustomer = new Customer();
        notInsertedCustomer.setName("Tomáš");
        notInsertedCustomer.setSurname("Hampl");
        notInsertedCustomer.setAddress("Botanická 68A, 602 00 Brno");
        notInsertedCustomer.setEmail("karel.novak@test.cz");
        notInsertedCustomer.setPhoneNumber(123456789L);
        notInsertedCustomer.setBirthday(LocalDate.of(1990, 3, 25));
        notInsertedCustomer.setPasswordHash("11aa");
        notInsertedCustomer.setNickName("cc.cc");
        notInsertedCustomer.setAdmin(true);

        insertCustomer(notInsertedCustomer);
    }

    private void setupSecondCustomer() {
        secondCustomer = new Customer();
        secondCustomer.setName("Tomáš");
        secondCustomer.setSurname("Zeman");
        secondCustomer.setAddress("Šumavská 120, Brno 602 00");
        secondCustomer.setEmail("hampl@ics.muni.cz");
        secondCustomer.setPhoneNumber(123456789L);
        secondCustomer.setBirthday(LocalDate.of(1990, 3, 25));
        secondCustomer.setPasswordHash("aa111");
        secondCustomer.setNickName("bb.bb");
        secondCustomer.setAdmin(false);

        insertCustomer(secondCustomer);
    }

    private void setupPrice() {
        price = new Price();
        price.setAmount(BigDecimal.ONE.setScale(2));
        price.setValidFrom(LocalDate.now());

        insertPrice(price);
    }

    private void setupReservation() {
        reservation = new Reservation();
        reservation.setTrip(trip);
        reservation.setCustomer(customer);
        reservation.setPrice(price);

        insertReservation(reservation);
    }

    private void setupNotInsertedReservation(){
        notInsertedReservation = new Reservation();
        notInsertedReservation.setTrip(trip);
        notInsertedReservation.setCustomer(notInsertedCustomer);
        notInsertedReservation.setPrice(price);
    }

    private void insertTrip(Trip trip) {
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

    private void insertPrice(Price price) {
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

    private void insertReservation(Reservation reservation) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(reservation);
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }

    private void removeReservation(Reservation reservation) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.contains(reservation) ? reservation : em.merge(reservation));
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }
}
