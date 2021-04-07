package cz.muni.fi.pa165.bluebat.entity;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.HashSet;

/**
 * @author : Rudolf Madoran
 * @since : 7. 4. 2021, Wed
 **/

@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class TestTripEntity extends AbstractTestNGSpringContextTests  {

    @PersistenceUnit
    private EntityManagerFactory emf;




    Trip preapeTrip() {
        Trip testTrip3 = new Trip();
        testTrip3.setAvailableTrips(5);
        testTrip3.setDateFrom(LocalDate.of(2020,5,1));
        testTrip3.setExcursions(new HashSet<Excursion>() {});
        testTrip3.setDestination("Madrid");
        testTrip3.setDateTo(LocalDate.of(2020,5,15));
        return  testTrip3;
    }

    void persistTrip(Trip trip) {

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

    @Test
    public void tripNullDestination() {
        Trip test = preapeTrip();
        test.setDestination(null);
        Assert.assertThrows(PersistenceException.class, () -> persistTrip(test));
    }
    @Test
    public void tripNullDateFrom() {
        Trip test = preapeTrip();
        test.setDateFrom(null);
        Assert.assertThrows(PersistenceException.class, () -> persistTrip(test));
    }
    @Test
    public void tripNullDateTo() {
        Trip test = preapeTrip();
        test.setDateTo(null);
        Assert.assertThrows(PersistenceException.class, () -> persistTrip(test));
    }



}
