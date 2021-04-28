package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dao.TripDao;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class TripServiceImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TripDao tripDao;

    @Autowired
    @InjectMocks
    private TripService tripService;



    private Trip prepareTrip() {

        Trip testTrip3 = new Trip();
        testTrip3.setAvailableTrips(5);
        testTrip3.setDateFrom(LocalDate.of(2022,5,1));
        testTrip3.setDestination("Madrid");
        testTrip3.setDateTo(LocalDate.of(2022,5,15));
        testTrip3.setName("Name");

        return  testTrip3;
    }



    @Test
    public void testCreateTrip() {
        Trip test = prepareTrip();
        Assert.assertEquals(tripService.findAll().size(),0);
        tripService.create(test);
        Assert.assertEquals(tripService.findAll().size(),1);

    }

    @Test
    public void testUpdateTrip() {
        Trip test = prepareTrip();
        tripService.create(test);
        Assert.assertEquals(tripService.findById(test.getId()).getDestination(),"Madrid");
        test.setDestination("Jamaica");
        tripService.update(test);
        Assert.assertEquals(tripService.findById(test.getId()).getDestination(),"Jamaica");
    }

    @Test
    public void testRemoveTrip() {

        Trip test = prepareTrip();
        Assert.assertEquals(tripService.findAll().size(),0);
        tripService.create(test);
        Assert.assertEquals(tripService.findAll().size(),1);
        tripService.delete(test);
        Assert.assertEquals(tripService.findAll().size(),0);


    }






}
