package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dto.ExcursionCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.ExcursionDTO;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.exceptions.NotFoundException;
import cz.muni.fi.pa165.bluebat.service.BeanMappingService;
import cz.muni.fi.pa165.bluebat.service.ExcursionService;
import cz.muni.fi.pa165.bluebat.service.TripService;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class ExcursionFacadeImplTest extends AbstractTestNGSpringContextTests {
    private static final Long TRIP_ID = 15L;

    @Autowired
    private BeanMappingService beanMappingService;

    @Mock
    private ExcursionService excursionService;

    @Mock
    private TripService tripService;

    private ExcursionFacade excursionFacade;

    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
        excursionFacade = new ExcursionFacadeImpl(excursionService, tripService, beanMappingService);
    }

    @Test
    public void createExcursion_valid() {
        Excursion excursion = getDefaultExcursion();
        ExcursionCreateDTO excursionCreateDTO = getDefaultExcursionCreateDTO();
        when(tripService.findById(TRIP_ID)).thenReturn(excursion.getTrip());

        excursionFacade.createExcursion(excursionCreateDTO);

        verify(excursionService, times(1)).create(excursion);
    }

    @Test
    public void createExcursion_invalidParentId() {
        ExcursionCreateDTO excursionCreateDTO = getDefaultExcursionCreateDTO();
        when(tripService.findById(any())).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> excursionFacade.createExcursion(excursionCreateDTO));
    }

    @Test
    public void createExcursion_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> excursionFacade.createExcursion(null));
    }

    @Test
    public void updateExcursion_valid() {
        Long id = 1L;
        Excursion excursion = getDefaultExcursion();
        excursion.setId(id);
        ExcursionDTO excursionDTO = getDefaultExcursionDTO();
        excursionDTO.setId(id);
        when(tripService.findById(TRIP_ID)).thenReturn(excursion.getTrip());

        excursionFacade.updateExcursion(excursionDTO);

        verify(excursionService, times(1)).update(excursion);
    }

    @Test
    public void updateExcursion_invalidParentId() {
        ExcursionDTO excursionDTO = getDefaultExcursionDTO();
        when(tripService.findById(any())).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> excursionFacade.updateExcursion(excursionDTO));
    }

    @Test
    public void updateExcursion_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> excursionFacade.updateExcursion(null));
    }

    @Test
    public void deleteExcursion_valid() {
        Long id = 1L;
        Excursion excursion = getDefaultExcursion();
        excursion.setId(id);
        when(excursionService.findById(id)).thenReturn(excursion);

        excursionFacade.deleteExcursion(id);

        verify(excursionService, times(1)).delete(excursion);
    }

    @Test
    public void deleteExcursion_nullId_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> excursionFacade.deleteExcursion(null));
    }

    @Test
    public void deleteExcursion_negative_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> excursionFacade.deleteExcursion(-1L));
    }

    @Test
    public void getExcursionById_valid() {
        Long id = 1L;
        Excursion excursion = getDefaultExcursion();
        excursion.setId(id);
        ExcursionDTO excursionDTO = getDefaultExcursionDTO();
        excursionDTO.setId(id);
        when(excursionService.findById(id)).thenReturn(excursion);

        ExcursionDTO result = excursionFacade.getExcursionById(id);

        verify(excursionService, times(1)).findById(id);
        Assert.assertEquals(result.getId(), excursionDTO.getId());
        Assert.assertEquals(result.getDateFrom(), excursionDTO.getDateFrom());
        Assert.assertEquals(result.getName(), excursionDTO.getName());
        Assert.assertEquals(result.getDestination(), excursionDTO.getDestination());
        Assert.assertEquals(result.getDescription(), excursionDTO.getDescription());
        Assert.assertEquals(result.getParentId(), excursionDTO.getParentId());
        Assert.assertEquals(result.getPrices(), excursionDTO.getPrices());
    }

    @Test
    public void getExcursionById_invalidId() {
        Long id = 1L;
        when(excursionService.findById(any())).thenReturn(null);

        excursionFacade.getExcursionById(1L);

        verify(excursionService, times(1)).findById(id);
    }

    @Test
    public void getExcursionById_nullId_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> excursionFacade.getExcursionById(null));
    }

    @Test
    public void getExcursionById_negative_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> excursionFacade.getExcursionById(-1L));
    }

    private static Excursion getDefaultExcursion() {
        Trip trip = new Trip();
        trip.setName("Test trip");
        trip.setDateFrom(LocalDate.of(2022, 4, 25));
        trip.setDateTo(LocalDate.of(2022, 5, 5));
        trip.setAvailableTrips(5);
        trip.setDestination("Madrid");
        trip.setId(TRIP_ID);

        Excursion result = new Excursion();
        result.setName("Test excursion");
        result.setDateFrom(LocalDate.of(2022, 4, 25));
        result.setDuration(Duration.ofHours(4));
        result.setDestination("Madrid");
        result.setDescription("climbing");
        result.setTrip(trip);

        return result;
    }

    private static ExcursionDTO getDefaultExcursionDTO() {
        ExcursionDTO result = new ExcursionDTO();
        result.setName("Test excursion");
        result.setDateFrom(LocalDate.of(2022, 4, 25));
        result.setDuration(Duration.ofHours(4));
        result.setDestination("Madrid");
        result.setDescription("climbing");
        result.setParentId(TRIP_ID);

        return result;
    }

    private static ExcursionCreateDTO getDefaultExcursionCreateDTO() {
        ExcursionCreateDTO result = new ExcursionCreateDTO();
        result.setName("Test excursion");
        result.setDateFrom(LocalDate.of(2022, 4, 25));
        result.setDuration(Duration.ofHours(4));
        result.setDestination("Madrid");
        result.setDescription("climbing");
        result.setParentId(TRIP_ID);

        return result;
    }
}
