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
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
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

    @PersistenceContext
    private EntityManager em;

    private Reservation reservation2;

    private Reservation reservation1;

    private Customer getCustomer1() {
        Customer result = new Customer();
        result.setName("Karel");
        result.setSurname("Novák");
        result.setAddress("Botanická 68A, 602 00 Brno");
        result.setEmail("karel.novak@test.cz");
        result.setPhoneNumber(123456789L);
        result.setBirthday(LocalDate.of(1990, 3, 25));

        return result;
    }

    private Customer getCustomer2() {
        Customer result = new Customer();
        result.setName("Petr");
        result.setSurname("Horák");
        result.setAddress("Vranovská 68A, 602 00 Brno");
        result.setEmail("petr.horak@test.cz");
        result.setPhoneNumber(123456987L);
        result.setBirthday(LocalDate.of(1995, 3, 25));

        return result;
    }

    private Price getPrice(){
        Price price=new Price();
        price.setAmount(BigDecimal.ZERO);
        price.setValidFrom(LocalDate.now());

        return price;
    }

    private Excursion getExcursion(){
        Excursion excursion = new Excursion();
        excursion.setName("testName");
        excursion.setDateFrom(LocalDate.now());
        excursion.setDestination("testDestination");
        excursion.setDescription("testDescription");

        return excursion;
    }

    private Trip getTrip(){
        Trip trip=new Trip();
        trip.setDateFrom(LocalDate.now());
        trip.setDestination("test");
        trip.setName("test");
        trip.setPrice(getPrice());
        trip.setDateTo(LocalDate.now());
        trip.setAvailableTrips(5);

        return trip;
    }

    @BeforeClass
    public void createTestData(){
        EntityManager em=null;

        reservation1 = new Reservation();
        reservation1.setTrip(getTrip());
        reservation1.setCustomer(getCustomer1());
        reservation1.setPrice(getPrice());

        reservation2 = new Reservation();
        reservation2.setTrip(getTrip());
        reservation2.setCustomer(getCustomer2());
        reservation2.setPrice(getPrice());

        try{
            em=emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(getCustomer1());
            em.persist(getCustomer2());
            em.persist(getPrice());
            em.persist(getTrip());
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }

    }

    @Test
    public void createFindUpdateDeleteTest(){
        reservationService.create(reservation1);
        reservationService.create(reservation2);
        Assert.assertEquals(reservationService.findById(reservation1.getId()).getCustomer().getSurname(),"Novák");
        Assert.assertEquals(reservationService.findAll().size(), 2);

        Customer updatedCustomer=new Customer();
        updatedCustomer=getCustomer1();
        updatedCustomer.setSurname("Dolák");
        reservation1.setCustomer(updatedCustomer);
        reservationService.update(reservation1);
        Assert.assertEquals(reservationService.findById(reservation1.getId()).getCustomer().getSurname(),"Dolák");

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
        assertThrows(InvalidDataAccessApiUsageException.class, () -> reservationService.delete(null));
    }
}
