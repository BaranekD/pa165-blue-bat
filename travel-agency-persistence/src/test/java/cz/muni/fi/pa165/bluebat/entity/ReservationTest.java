package cz.muni.fi.pa165.bluebat.entity;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Ondrej Vaca
 */
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Reservation reservation;

    private void persistReservation(Reservation r) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(r);
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }

    private void persistTrip(Trip t) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }

    private void persistCustomer(Customer c) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }

    private void persistPrice(Price p) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }

    private void getFullyInitializedReservation() {
        reservation = new Reservation();
        reservation.setCustomer(getInitializedCustomer());
        reservation.setPrice(getInitializedPrice());
        reservation.setTrip(getInitializeTrip());
    }

    private Trip getInitializeTrip() {
        Trip trip = new Trip();
        trip.setName("Slovakia");
        trip.setDateTo(LocalDate.now().plusDays(2));
        trip.setDateFrom(LocalDate.now().plusDays(1));
        trip.setDestination("Slovakia");
        trip.setAvailableTrips(5);

        persistTrip(trip);

        return trip;
    }

    private Customer getInitializedCustomer(){
        Customer customer = new Customer();
        customer.setName("Karel");
        customer.setSurname("Novák");
        customer.setAddress("Botanická 68A, 602 00 Brno");
        customer.setEmail("karel.novak@test.cz");
        customer.setPhoneNumber(123456789L);
        customer.setBirthday(LocalDate.of(1990, 3, 25));
        customer.setPasswordHash("ab123");
        customer.setNickName("ab.ba");
        customer.setAdmin(true);

        persistCustomer(customer);

        return customer;
    }

    private Price getInitializedPrice() {
        Price price = new Price();
        price.setAmount(BigDecimal.TEN);
        price.setValidFrom(LocalDate.now());

        persistPrice(price);

        return price;
    }

    @Test
    public void reservation_fullyInitialized() {
        getFullyInitializedReservation();
        Assertions.assertDoesNotThrow(() -> persistReservation(reservation));
        Reservation found;
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            found = em.find(Reservation.class, reservation.getId());
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }

        Assert.assertEquals(found, reservation);
    }

    @Test
    public void reservationTrip_null() {
        getFullyInitializedReservation();
        reservation.setTrip(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> persistReservation(reservation));
    }

    @Test
    public void reservationPrice_null() {
        getFullyInitializedReservation();
        reservation.setPrice(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> persistReservation(reservation));
    }

    @Test
    public void reservationCustomer_null() {
        getFullyInitializedReservation();
        reservation.setCustomer(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> persistReservation(reservation));
    }
}
