package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dto.TripCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.TripDTO;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.service.*;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TripFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanMappingService beanMappingService;

    @Mock
    private TripService tripService;

    private TripFacade tripFacade;

    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);

        when(tripService.findById(any())).thenReturn(new Trip());

        tripFacade = new TripFacadeImpl(tripService,beanMappingService);
    }

    private TripCreateDTO prepareTripCreateDTO() {
        TripCreateDTO testTrip3 = new TripCreateDTO();
        testTrip3.setAvailableTrips(5);
        testTrip3.setDateFrom(LocalDate.of(2022,5,1));
        testTrip3.setDestination("Madrid");
        testTrip3.setDateTo(LocalDate.of(2022,5,15));
        testTrip3.setName("Name");

        return  testTrip3;
    }

    private TripDTO prepareTripDTO() {
        TripDTO testTrip3 = new TripDTO();
        testTrip3.setId(1L);
        testTrip3.setAvailableTrips(5);
        testTrip3.setDateFrom(LocalDate.of(2022,5,1));
        testTrip3.setDestination("Madrid");
        testTrip3.setDateTo(LocalDate.of(2022,5,15));
        testTrip3.setName("Name");

        return  testTrip3;
    }

    private Trip prepareTrip() {
        Trip testTrip3 = new Trip();
        testTrip3.setId(1L);
        testTrip3.setAvailableTrips(5);
        testTrip3.setDateFrom(LocalDate.of(2022,5,1));
        testTrip3.setDestination("Madrid");
        testTrip3.setDateTo(LocalDate.of(2022,5,15));
        testTrip3.setName("Name");

        return  testTrip3;
    }

    @Test
    public void getTripById_valid() {
        Trip trip = prepareTrip();
        TripDTO dto = prepareTripDTO();

        when(tripService.findById(trip.getId())).thenReturn(trip);

        TripDTO result = tripFacade.getTripDTO(trip.getId());

        verify(tripService, times(1)).findById(trip.getId());
        Assert.assertEquals(result, dto);
    }

    @Test
    public void testCreateTrip() {
        tripFacade.createTrip(prepareTripCreateDTO());
        verify(tripService, times(1)).create(any());
    }

    @Test
    public void testUpdateTrip() {
        tripFacade.updateTrip(prepareTripDTO());
        verify(tripService, times(1)).update(any());
    }

    @Test
    public void testDeleteTrip() {
        tripFacade.deleteTrip(1L);
        verify(tripService, times(1)).delete(any());
    }

    @Test
    public void testAllTrip() {
        tripFacade.getAllTrips();
        verify(tripService, times(1)).findAll();
    }
}
