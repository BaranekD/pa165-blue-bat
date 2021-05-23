package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dao.TripDao;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author : Rudolf Madoran
 * @since : 29. 4. 2021, Thu
 **/
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TripServiceImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TripDao tripDao;

    private TripService tripService;

    @Mock
    private PriceService priceService;

    @Mock
    private ExcursionService excursionService;

    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
        tripService = new TripServiceImpl(tripDao,priceService,excursionService);

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
        test.setId(1L);
        when(tripService.findById(test.getId())).thenReturn(test);
        tripService.update(test);
        verify(tripDao, times(1)).update(test);
        verify(priceService, times(1)).updatePrices(any(),any());
    }

    @Test
    public void testDeleteTrip() {
        Trip test = prepareTrip();
        test.setId(1L);
        tripService.delete(test);
        verify(tripDao, times(1)).delete(test);
    }

    @Test
    public void create_null_IllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> tripService.create(null));
    }

    @Test
    public void update_null_IllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> tripService.update(null));
    }

    @Test
    public void delete_null_IllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> tripService.delete(null));
    }

    @Test
    public void findId_IllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> tripService.findById(null));
    }

    @Test
    public void delete_daoThrowsException_WrongDataAccessException() {
        Trip test = prepareTrip();
        when(tripDao.findById(test.getId())).thenReturn(test);

        doThrow(new IllegalArgumentException()).when(tripDao).delete(test);
        Assertions.assertThrows(WrongDataAccessException.class, () -> tripService.delete(test));
    }

    @Test
    public void update_daoThrowsException_WrongDataAccessException() {
        Trip test = prepareTrip();
        when(tripDao.findById(test.getId())).thenReturn(test);

        doThrow(new IllegalArgumentException()).when(tripDao).update(test);
        Assertions.assertThrows(WrongDataAccessException.class, () -> tripService.update(test));
    }

    @Test
    public void create_daoThrowsException_WrongDataAccessException() {
        Trip test = prepareTrip();

        doThrow(new IllegalArgumentException()).when(tripDao).create(test);
        Assertions.assertThrows(WrongDataAccessException.class, () -> tripService.create(test));
    }

    @Test
    public void findByID_daoThrowsException_WrongDataAccessException() {
        doThrow(new IllegalArgumentException()).when(tripDao).findById(1L);
        Assertions.assertThrows(WrongDataAccessException.class, () -> tripService.findById(1L));
    }

    @Test
    public void findALL_daoThrowsException_WrongDataAccessException() {
        doThrow(new IllegalArgumentException()).when(tripDao).findAll();
        Assertions.assertThrows(WrongDataAccessException.class, () -> tripService.findAll());
    }
}
