package cz.muni.fi.pa165.bluebat.service.facade;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dto.ExcursionDTO;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.facade.ExcursionFacade;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class ExcursionFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Mock
    private ExcursionService excursionService;

    @Mock
    private TripService tripService;

    private ExcursionFacade excursionFacade;

    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
        excursionFacade = new ExcursionFacadeImpl(excursionService, tripService);
    }

    @Test
    public void createExcursion_valid() {
        Excursion excursion = getDefaultExcursion();
        ExcursionDTO excursionDTO = getDefaultExcursionDTO();
        when(tripService.findById(excursion.getTrip().getId())).thenReturn(excursion.getTrip());

        excursionFacade.createExcursion(excursionDTO);

        verify(excursionService, times(1)).create(excursion);
    }

    @Test
    public void createExcursion_invalidParentId() {
        ExcursionDTO excursionDTO = getDefaultExcursionDTO();
        when(tripService.findById(any())).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> excursionFacade.createExcursion(excursionDTO));
    }

    @Test
    public void updateExcursion_valid() {
        Long id = 1L;
        Excursion excursion = getDefaultExcursion();
        excursion.setId(id);
        ExcursionDTO excursionDTO = getDefaultExcursionDTO();
        excursionDTO.setId(id);
        when(tripService.findById(excursion.getTrip().getId())).thenReturn(excursion.getTrip());

        excursionFacade.updateExcursion(excursionDTO);

        verify(excursionService, times(1)).update(excursion);
    }


    @Test
    public void updateExcursion_invalidParentId() {
        ExcursionDTO excursionDTO = getDefaultExcursionDTO();
        when(tripService.findById(any())).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> excursionFacade.updateExcursion(excursionDTO));
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

    private static Excursion getDefaultExcursion() {
        Trip trip = new Trip();
        trip.setName("Test trip");
        trip.setDateFrom(LocalDate.of(2022, 4, 25));
        trip.setDateTo(LocalDate.of(2022, 5, 5));
        trip.setAvailableTrips(5);
        trip.setDestination("Madrid");
        trip.setId(15L);

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
        result.setParentId(15L);

        return result;
    }
}
