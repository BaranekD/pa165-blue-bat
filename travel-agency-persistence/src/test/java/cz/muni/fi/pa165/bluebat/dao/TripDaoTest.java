package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDate;

/**
 * Test for dao of Trip entity.
 *
 * @author : Rudolf Madoran
 * @since : 7. 4. 2021, Wed
 **/
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TripDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    TripDao service;

    public Trip testTrip = null;
    public Trip testTrip2 = null;
    public Trip testTrip3 = null;

    @BeforeClass
    void setup() {
        testTrip = new Trip();
        testTrip.setAvailableTrips(5);
        testTrip.setDateFrom(LocalDate.of(2022,5,1));
        testTrip.setDestination("Turkey");
        testTrip.setDateTo(LocalDate.of(2022,5,15));
        testTrip.setName("Name1");

        testTrip2 = new Trip();
        testTrip2.setAvailableTrips(5);
        testTrip2.setDateFrom(LocalDate.of(2022,5,1));
        testTrip2.setDestination("Japan");
        testTrip2.setDateTo(LocalDate.of(2022,5,15));
        testTrip2.setName("Name2");

        testTrip3 = new Trip();
        testTrip3.setAvailableTrips(3);
        testTrip3.setDateFrom(LocalDate.of(2022,4,1));
        testTrip3.setDestination("Portugal");
        testTrip3.setDateTo(LocalDate.of(2022,5,15));
        testTrip3.setName("Name3");
    }

    @Test
    void createTest() {
        service.create(testTrip);
        Assert.assertEquals(service.findById(testTrip.getId()),testTrip);
    }

    @Test
    void updateTest() {
        service.create(testTrip2);
        Trip found = service.findById(testTrip2.getId());
        found.setDestination("England");
        service.update(found);
        Assert.assertEquals(service.findById(found.getId()).getDestination(),"England");
    }

    @Test
    void deleteTest() {
        service.create(testTrip3);
        for (Trip trip: service.findAll()) {
            Assert.assertEquals(trip.getDestination(),"Portugal");
        }
        Assert.assertEquals(service.findAll().size(),1);
        service.delete(service.findById(testTrip3.getId()));
        Assert.assertEquals(service.findAll().size(),0);
    }

    @Test
    void findNullTest() {
        Assert.assertThrows( IllegalArgumentException.class,() -> service.findById(null));
    }

    @Test
    void deleteNullTest() {
        Assert.assertThrows( IllegalArgumentException.class,() -> service.delete(null));
    }

    @Test
    void createNullTest() {
        Assert.assertThrows( IllegalArgumentException.class,() -> service.create(null));
    }

    @Test
    void findNonExistingTripTest() {
        Assert.assertNull(service.findById(150L));
    }

    @Test
    void createWithNullTest() {
        Assert.assertThrows( IllegalArgumentException.class,() -> service.create(null));
    }
}
