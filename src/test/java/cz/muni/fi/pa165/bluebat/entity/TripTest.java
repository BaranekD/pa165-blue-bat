package cz.muni.fi.pa165.bluebat.entity;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

/**
 * @author : Rudolf Madoran
 * @since : 7. 4. 2021, Wed
 **/

@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class TripTest extends AbstractTestNGSpringContextTests  {

    @PersistenceUnit
    private EntityManagerFactory emf;



    @DataProvider(name = "valuesInvalidStrings")
    private static Object[][] getInvalidStrings() {
        return new String[][] { { null }, { "" }, { " " }, { "\t" }, { "\n" } };
    }

    @DataProvider(name = "valuesInvalidDates")
    private static Object[][] getInvalidDates() {

        return new LocalDate[][] { { null },{LocalDate.of(2020,5,1)} , {LocalDate.of(1878,5,1)} ,
                {LocalDate.of(1,1,1)}};
    }

    @DataProvider(name = "valuesInvalidAvailability")
    private static Object[][] getInvalidAvailability() {

        return new Integer[][] {{null},{-5},{-10}};
    }


    @AfterClass
    private void removeTrips() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            for (Trip trip: findAll()) {
                em.remove(em.contains(trip) ? trip : em.merge(trip));
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }



    Trip prepareTrip() {
        Trip testTrip3 = new Trip();
        testTrip3.setAvailableTrips(5);
        testTrip3.setDateFrom(LocalDate.of(2022,5,1));
        testTrip3.setDestination("Madrid");
        testTrip3.setDateTo(LocalDate.of(2022,5,15));
        testTrip3.setName("Name");
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
    public List<Trip> findAll(){
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            List<Trip>  list = em.createQuery("select t from Trip t",Trip.class).getResultList() ;
            em.getTransaction().commit();
            return list;
        } finally {
            if (em != null) em.close();
        }

    }

    @Test(dataProvider = "valuesInvalidAvailability")
    public void tripInvalidAvailability(Integer data) {
        Trip test = prepareTrip();
        test.setAvailableTrips(data);
        Assert.assertThrows(ConstraintViolationException.class, () -> persistTrip(test));
    }

    @Test(dataProvider = "valuesInvalidStrings")
    public void tripInvalidName(String data) {
        Trip test = prepareTrip();
        test.setName(data);
        Assert.assertThrows(ConstraintViolationException.class, () -> persistTrip(test));
    }

    @Test(dataProvider = "valuesInvalidDates")
    public void tripInvalidDateFrom(LocalDate data) {
        Trip test = prepareTrip();
        test.setDateFrom(data);
        Assert.assertThrows(ConstraintViolationException.class, () -> persistTrip(test));

    }
    @Test(dataProvider = "valuesInvalidDates")
    public void tripInvalidDateTo(LocalDate data) {
        Trip test = prepareTrip();
        test.setDateTo(data);
        Assert.assertThrows(ConstraintViolationException.class, () -> persistTrip(test));
    }

    @Test(dataProvider = "valuesInvalidStrings")
    public void tripInvalidDestination(String data) {
        Trip test = prepareTrip();
        test.setDestination(data);
        Assert.assertThrows(ConstraintViolationException.class, () -> persistTrip(test));
    }



}
