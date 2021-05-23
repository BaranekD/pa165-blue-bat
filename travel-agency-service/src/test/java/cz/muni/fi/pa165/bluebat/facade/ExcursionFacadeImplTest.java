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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class ExcursionFacadeImplTest extends AbstractTestNGSpringContextTests {
    private static final Long TRIP_ID = 15L;
    private static final Long EXCURSION_ID = 3L;

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private ExcursionService excursionService;

    @Mock
    private TripService tripService;

    private ExcursionFacade excursionFacade;

    Trip trip;
    Excursion excursion;
    Excursion insertedExcursion;
    ExcursionDTO excursionDTO;
    ExcursionCreateDTO excursionCreateDTO;

    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
        excursionFacade = new ExcursionFacadeImpl(excursionService, tripService, beanMappingService);

        setUpTrip();
        excursion = getDefaultExcursion();
        insertedExcursion = getDefaultInsertedExcursion();
        excursionDTO = getDefaultExcursionDTO();
        excursionCreateDTO = getDefaultExcursionCreateDTO();

        when(beanMappingService.mapTo(getDefaultInsertedExcursion(), ExcursionDTO.class)).thenReturn(getDefaultExcursionDTO());
        when(beanMappingService.mapTo(getDefaultExcursionDTO(), Excursion.class)).thenReturn(getDefaultInsertedExcursion());
        when(beanMappingService.mapTo(getDefaultExcursionCreateDTO(), Excursion.class)).thenReturn(getDefaultExcursion());

        doAnswer(invocation -> {
            ((Excursion)invocation.getArgument(0)).setId(EXCURSION_ID);
            ((Excursion)invocation.getArgument(0)).setTrip(invocation.getArgument(1));
            return null;
        }).when(excursionService).create(any(), any());
        doAnswer(invocation -> {
            ((Excursion)invocation.getArgument(0)).setTrip(trip);
            return null;
        }).when(excursionService).update(any());
    }

    @Test
    public void createExcursion_valid() {
        when(tripService.findById(TRIP_ID)).thenReturn(trip);

        ExcursionDTO result = excursionFacade.createExcursion(excursionCreateDTO);

        verify(excursionService, times(1)).create(any(), any());
        Assert.assertEquals(result, getDefaultExcursionDTO());
    }

    @Test
    public void createExcursion_invalidParentId() {
        when(tripService.findById(any())).thenReturn(null);

        Assertions.assertThrows(NotFoundException.class, () -> excursionFacade.createExcursion(excursionCreateDTO));
    }

    @Test
    public void createExcursion_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> excursionFacade.createExcursion(null));
    }

    @Test
    public void updateExcursion_valid() {
        ExcursionDTO result = excursionFacade.updateExcursion(excursionDTO);

        verify(excursionService, times(1)).update(any());
        Assert.assertEquals(result, excursionDTO);
    }

    @Test
    public void updateExcursion_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> excursionFacade.updateExcursion(null));
    }

    @Test
    public void deleteExcursion_valid() {
        when(excursionService.findById(EXCURSION_ID)).thenReturn(excursion);

        excursionFacade.deleteExcursion(EXCURSION_ID);

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

//    @Test
//    public void getExcursionById_valid() {
//        when(excursionService.findById(EXCURSION_ID)).thenReturn(getDefaultInsertedExcursion());
//
//        ExcursionDTO result = excursionFacade.getExcursionById(EXCURSION_ID);
//
//        verify(excursionService, times(1)).findById(EXCURSION_ID);
//        Assert.assertEquals(result, excursionDTO);
//    }

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

    private void setUpTrip() {
        trip = new Trip();
        trip.setName("Test trip");
        trip.setDateFrom(LocalDate.of(2022, 4, 25));
        trip.setDateTo(LocalDate.of(2022, 5, 5));
        trip.setAvailableTrips(5);
        trip.setDestination("Madrid");
        trip.setId(TRIP_ID);
    }

    private Excursion getDefaultExcursion() {
        Excursion excursion = new Excursion();
        excursion.setName("Test excursion");
        excursion.setDateFrom(LocalDate.of(2022, 4, 25));
        excursion.setDuration(Duration.ofHours(4));
        excursion.setDestination("Madrid");
        excursion.setDescription("climbing");

        return excursion;
    }

    private Excursion getDefaultInsertedExcursion() {
        Excursion excursion = getDefaultExcursion();
        excursion.setId(EXCURSION_ID);
        excursion.setTrip(trip);
        return excursion;
    }

    private ExcursionDTO getDefaultExcursionDTO() {
        ExcursionDTO result = new ExcursionDTO();
        result.setId(EXCURSION_ID);
        result.setName("Test excursion");
        result.setDateFrom(LocalDate.of(2022, 4, 25));
        result.setDuration(Duration.ofHours(4));
        result.setDestination("Madrid");
        result.setDescription("climbing");

        return result;
    }

    private ExcursionCreateDTO getDefaultExcursionCreateDTO() {
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
