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

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
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

    private Excursion excursion1;

    private Excursion excursion2;

    @BeforeClass
    public void createTestData(){
        trip1 = new Trip();
        trip1.setName("Trip1");
        trip2 = new Trip();
        trip2.setName("Trip2");
        updatedTrip = new Trip();
        updatedTrip.setName("UpdatedTrip");

        customer1 = new Customer();
        customer1.setSurname("Customer1");
        customer2=new Customer();
        customer2.setSurname("Customer2");

        price1 = new Price();
        price2 = new Price();

        excursion1 = new Excursion();
        excursion2 = new Excursion();
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
    }

    @Test
    public void createFindUpdateDeleteTest(){
        reservationService.create(reservation1);
        reservationService.create(reservation2);
        Assert.assertEquals(reservationService.findById(reservation1.getId()).getTrip().getName(),"Trip1");
        Assert.assertEquals(reservationService.findById(reservation1.getId()).getCustomer().getName(),"Customer1");
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
        assertThrows(InvalidDataAccessApiUsageException.class, () -> reservationService.delete(null));
    }





}
