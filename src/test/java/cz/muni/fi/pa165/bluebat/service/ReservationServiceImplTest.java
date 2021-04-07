package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Reservation;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertNull;

/**
 * @author Ondrej Vaca
 *
 */

@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class ReservationServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ReservationService reservationService;

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Reservation reservation1;

    private Reservation reservation2;

    private Trip trip1;

    private Trip trip2;

    private Trip updatedTrip;

    private Customer customer1;

    private Customer customer2;

    private Price price1;

    private Price price2;

    private Price price3;

    private Price price4;

    private Excursion excursion1;

    private Excursion excursion2;

    @BeforeClass
    public void createTestData(){
        trip1 = new Trip();
        trip1.setName("Trip1");
        trip1.setDateFrom(LocalDate.of(2020, 3, 3));
        trip1.setDateTo(LocalDate.of(2020, 4, 3));
        trip1.setDestination("CZE");
        trip1.setAvailableTrips(5);
        trip2 = new Trip();
        trip2.setName("Trip2");
        trip2.setDateFrom(LocalDate.of(2020, 3, 3));
        trip2.setDateTo(LocalDate.of(2020, 4, 3));
        trip2.setDestination("CZE");
        trip2.setAvailableTrips(5);
        updatedTrip = new Trip();
        updatedTrip.setName("UpdatedTrip");
        updatedTrip.setDateFrom(LocalDate.of(2020, 3, 3));
        updatedTrip.setDateTo(LocalDate.of(2020, 4, 3));
        updatedTrip.setDestination("CZE");
        updatedTrip.setAvailableTrips(5);

        customer1 = new Customer();
        customer1.setSurname("Customer1");
        customer1.setSurname("Novák");
        customer1.setAddress("Botanická 68A, 602 00 Brno");
        customer1.setEmail("karel.novak@test.cz");
        customer1.setPhoneNumber(123456789L);
        customer1.setBirthday(LocalDate.of(1990, 3, 25));
        customer2=new Customer();
        customer2.setSurname("Customer2");
        customer2.setSurname("Novák");
        customer2.setAddress("Botanická 68A, 602 00 Brno");
        customer2.setEmail("karel.novak@test.cz");
        customer2.setPhoneNumber(123456789L);
        customer2.setBirthday(LocalDate.of(1990, 3, 25));

        price1 = new Price();
        price1.setAmount(new BigDecimal(15));
        price1.setValidFrom(LocalDate.of(2020, 3, 3));
        price2 = new Price();
        price2.setAmount(new BigDecimal(105));
        price2.setValidFrom(LocalDate.of(2020, 3, 3));
        price3 = new Price();
        price3.setAmount(new BigDecimal(15));
        price3.setValidFrom(LocalDate.of(2020, 3, 3));
        price4 = new Price();
        price4.setAmount(new BigDecimal(15));
        price4.setValidFrom(LocalDate.of(2020, 3, 3));

        excursion1 = new Excursion();
        excursion1.setDateFrom(LocalDate.of(2020, 1, 1));
        excursion1.setDuration(new Timestamp(15));
        excursion1.setDestination("CZE");
        excursion1.addPrice(price3);
        excursion2 = new Excursion();
        excursion2.setDateFrom(LocalDate.of(2020, 1, 1));
        excursion2.setDuration(new Timestamp(15));
        excursion2.setDestination("CZE");
        excursion2.addPrice(price4);
        Set<Excursion> excursions = new HashSet<>();
        excursions.add(excursion1);
        excursions.add(excursion2);

        reservation1 = new Reservation();
        reservation1.setTrip(trip1);
        reservation1.setCustomer(customer1);
        reservation1.setPrice(price1);
        reservation1.setExcursions(excursions);

        reservation2 = new Reservation();
        reservation2.setTrip(trip2);
        reservation2.setCustomer(customer2);
        reservation2.setPrice(price2);

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(price1);
            em.persist(price2);
            em.persist(price3);
            em.persist(price4);
            em.persist(customer1);
            em.persist(customer2);
            em.persist(excursion1);
            em.persist(excursion2);
            em.persist(trip1);
            em.persist(trip2);
            em.persist(updatedTrip);

            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }

    @Test
    public void createFindUpdateDeleteTest(){
        reservationService.create(reservation1);
        reservationService.create(reservation2);
        Assert.assertEquals(reservationService.findById(reservation1.getId()).getTrip().getName(),"Trip1");
        Assert.assertEquals(reservationService.findAll().size(), 2);

        reservation1.setTrip(updatedTrip);
        reservationService.update(reservation1);
        Assert.assertEquals(reservationService.findById(reservation1.getId()).getTrip().getName(),"UpdatedTrip");

        reservationService.delete(reservation2);
        Assert.assertEquals(reservationService.findAll().size(), 1);
    }

    @Test
    public void findReservationNonExistingIdReturnsNull() {
        Reservation r = reservationService.findById(2048L);
        assertNull(r);
    }

    @Test
    public void createReservationNullArgumentThrowsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> reservationService.create(null));
    }

    @Test
    public void updateReservationNullArgumentThrowsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> reservationService.update(null));
    }

    @Test
    public void deleteReservationNullArgumentThrowsInvalidDataAccessApiUsageException() {
        assertThrows(NullPointerException.class, () -> reservationService.delete(null));
    }
}