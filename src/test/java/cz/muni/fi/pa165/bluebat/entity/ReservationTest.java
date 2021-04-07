package cz.muni.fi.pa165.bluebat.entity;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import org.aspectj.lang.annotation.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertThrows;

/**
 * @author Ondrej Vaca
 *
 */

@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class ReservationTest extends AbstractTestNGSpringContextTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @BeforeClass
    public Reservation createReservation() {
        Trip trip = new Trip();
        Customer customer = new Customer();
        Price price = new Price();
        Excursion excursion1 = new Excursion();
        Excursion excursion2 = new Excursion();
        Set<Excursion> excursions = new HashSet<>();
        excursions.add(excursion1);
        excursions.add(excursion2);

        Reservation reservation = new Reservation();
        reservation.setTrip(trip);
        reservation.setCustomer(customer);
        reservation.setPrice(price);
        reservation.setExcursions(excursions);

        return reservation;
    }

    @Test
    public void nullTripThrowsPersistenceException() {
        assertThrows(PersistenceException.class, () -> {
            Reservation r = createReservation();
            r.setTrip(null);
            EntityManager em = null;
            try {
                em = emf.createEntityManager();
                em.getTransaction().begin();
                em.persist(r);
                em.getTransaction().commit();
            } finally {
                if (em != null) em.close();
            }
        });
    }
}


