package cz.muni.fi.pa165.bluebat.service;


import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.LocalDate;
import java.util.HashSet;


/**
 * Test for service of Trip entity.
 *
 * @author : Rudolf Madoran
 * @since : 7. 4. 2021, Wed
 **/
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class TripServiceImplTest extends AbstractTestNGSpringContextTests {
    /*
    @Autowired
    TripService service;


    public Trip testTrip = null;
    public Trip testTrip2 = null;



    @BeforeClass
    void setup() {
        testTrip = new Trip();
        testTrip.setAvailableTrips(5);
        testTrip.setDateFrom(LocalDate.of(2020,5,1));
        testTrip.setDestination("Turkey");
        testTrip.setExcursions(new HashSet<Excursion>() {});
        testTrip.setDateTo(LocalDate.of(2020,5,15));

        testTrip2 = new Trip();
        testTrip2.setAvailableTrips(5);
        testTrip2.setDateFrom(LocalDate.of(2020,5,1));
        testTrip2.setDestination("Japan");
        testTrip2.setExcursions(new HashSet<Excursion>() {});
        testTrip2.setDateTo(LocalDate.of(2020,5,15));

    }




    @Test
    void createTest() {
        service.create(testTrip);
        Assert.assertEquals(service.findById(testTrip.getId()),testTrip);
    }



    @Test
    void updateTest() {
        Trip found = service.findById(testTrip.getId());
        found.setDestination("England");
        service.update(found);
        Assert.assertEquals(service.findById(testTrip.getId()).getDestination(),"England");
    }

    @Test
    void deleteTest() {
        service.create(testTrip2);
        Assert.assertEquals(service.findAll().size(),2);
        service.delete(service.findById(testTrip2.getId()));
        Assert.assertEquals(service.findAll().size(),1);
    }

    @Test
    void findNullTest() {
        Assert.assertThrows( InvalidDataAccessApiUsageException.class,() -> service.findById(null));
    }

    @Test
    void deleteNullTest() {
        Assert.assertThrows( InvalidDataAccessApiUsageException.class,() -> service.delete(null));
    }

    @Test
    void createNullTest() {
        Assert.assertThrows( InvalidDataAccessApiUsageException.class,() -> service.create(null));
    }

    @Test
    void findNonExistingTripTest() {
        Assert.assertNull(service.findById(150L));
    }
    */
}
