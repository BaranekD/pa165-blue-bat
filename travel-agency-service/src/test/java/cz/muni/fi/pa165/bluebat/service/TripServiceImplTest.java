package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dao.TripDao;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.doNothing;

@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TripServiceImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TripDao tripDao;


    private TripService tripService;



    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
       tripService = new TripServiceImpl(tripDao);

        doNothing().when(tripDao).create(any());
        doNothing().when(tripDao).update(any());
        doNothing().when(tripDao).delete(any());
    }





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
        tripService.create(test);
        verify(tripDao, times(1)).create(test);

    }

    @Test
    public void testUpdateTrip() {
        Trip test = prepareTrip();
        Long id = test.getId();
        when(tripService.findById(id)).thenReturn(test);
        tripService.update(test);
        verify(tripDao, times(1)).update(test);
    }

    @Test
    public void testDeleteTrip() {

        Trip test = prepareTrip();
        tripService.delete(test);
        verify(tripDao, times(1)).delete(test);


    }
    @Test
    public void update_null_IllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class,
                () -> tripService.update(null));
    }

    @Test
    public void findId_IllegalArgumentException() {
        Trip test = prepareTrip();
        Long id = test.getId();
        when(tripService.findById(id)).thenReturn(null);
        Assert.assertThrows(IllegalArgumentException.class,
                () -> tripService.update(test));
    }








}
