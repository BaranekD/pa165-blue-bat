package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dao.PriceDao;
import cz.muni.fi.pa165.bluebat.dao.TripDao;
import cz.muni.fi.pa165.bluebat.dto.TripCreateDTO;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.service.PriceService;
import cz.muni.fi.pa165.bluebat.service.PriceServiceImpl;
import cz.muni.fi.pa165.bluebat.service.TripService;
import cz.muni.fi.pa165.bluebat.service.TripServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
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

@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TripFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TripDao tripDao;

    private TripService tripService;

    @Mock
    private PriceDao priceDao;

    private PriceService priceService;

    private TripFacade tripFacade;


    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
        tripService = new TripServiceImpl(tripDao);

        doNothing().when(tripDao).create(any());
        doNothing().when(tripDao).update(any());
        doNothing().when(tripDao).delete(any());
        when(tripService.findById(any())).thenReturn(new Trip());


        priceService = new PriceServiceImpl(priceDao);

        doNothing().when(priceDao).create(any());
        doNothing().when(priceDao).update(any());
        doNothing().when(priceDao).delete(any());
        when(priceDao.findById(any())).thenReturn(new Price());

        tripFacade = new TripFacadeImpl(tripService,priceService);
    }

    private TripCreateDTO prepareTrip() {

        TripCreateDTO testTrip3 = new TripCreateDTO();
        testTrip3.setAvailableTrips(5);
        testTrip3.setDateFrom(LocalDate.of(2022,5,1));
        testTrip3.setDestination("Madrid");
        testTrip3.setDateTo(LocalDate.of(2022,5,15));
        testTrip3.setName("Name");

        return  testTrip3;
    }

    /*
    public void createTrip(TripCreateDTO dto);
    public void updateTrip(Long tripId, TripCreateDTO dto);
    public void addPrice(Long tripId, Long priceId);
    public void removePrice(Long tripId, Long priceId);
    public void deleteTrip(Long tripId);
    */

    @Test
    public void testCreateTrip() {
        tripFacade.createTrip(prepareTrip());
        verify(tripDao, times(1)).create(any());
    }
    @Test
    public void testUpdateTrip() {

        tripFacade.updateTrip(1L,prepareTrip());
        verify(tripDao, times(1)).update(any());
    }
    @Test
    public void testAddPrice() {
        tripFacade.addPrice(1L,1L);
        verify(tripDao, times(1)).findById(any());
        verify(priceDao, times(1)).findById(any());
    }
    @Test
    public void testRemovePrice() {
        tripFacade.removePrice(1L,1L);
        verify(tripDao, times(1)).findById(any());
        verify(priceDao, times(1)).findById(any());
    }
    @Test
    public void testDeleteTrip() {
        tripFacade.deleteTrip(1L);
        verify(tripDao, times(1)).delete(any());
    }
}

