package cz.muni.fi.pa165.bluebat.service.facade;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dto.CustomerDTO;
import cz.muni.fi.pa165.bluebat.dto.PriceDTO;
import cz.muni.fi.pa165.bluebat.dto.ReservationCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.ReservationDTO;
import cz.muni.fi.pa165.bluebat.dto.TripDTO;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Reservation;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.facade.ReservationFacade;
import cz.muni.fi.pa165.bluebat.service.BeanMappingService;
import cz.muni.fi.pa165.bluebat.service.ReservationService;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class ReservationFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanMappingService beanMappingService;

    @Mock
    private ReservationService reservationService;

    private ReservationFacade reservationFacade;

    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
        reservationFacade = new ReservationFacadeImpl(reservationService, beanMappingService);
    }

    @Test
    public void createReservation_valid() {
        Reservation reservation = getDefaultReservation();
        ReservationCreateDTO reservationCreateDTO = getDefaultReservationCreateDTO();

        reservationFacade.createReservation(reservationCreateDTO);

        verify(reservationService, times(1)).create(reservation);
    }

    @Test
    public void updateReservation_valid() {
        Long id = 1L;

        Reservation reservation = getDefaultReservation();
        reservation.setId(id);

        ReservationDTO reservationDTO = getDefaultReservationDTO();
        reservationDTO.setId(id);

        reservationFacade.updateReservation(reservationDTO);
        verify(reservationService, times(1)).update(reservation);
    }

    @Test
    public void deleteReservation_valid() {
        Long id = 1L;

        Reservation reservation = getDefaultReservation();
        reservation.setId(id);

        when(reservationService.findById(id)).thenReturn(reservation);
        reservationFacade.deleteReservation(id);
        verify(reservationService, times(1)).delete(reservation);
    }

    @Test
    public void getReservationById_valid() {
        Long id = 1L;

        Reservation reservation = getDefaultReservation();
        reservation.setId(id);

        ReservationDTO reservationDTO = getDefaultReservationDTO();
        reservationDTO.setId(id);

        when(reservationService.findById(id)).thenReturn(reservation);
        ReservationDTO result = reservationFacade.getReservationById(id);

        verify(reservationService, times(1)).findById(id);
        Assert.assertEquals(result.getId(), reservationDTO.getId());
        Assert.assertEquals(result.getTripDTO().getName(), reservationDTO.getTripDTO().getName());
        Assert.assertEquals(result.getCustomerDTO().getName(), reservationDTO.getCustomerDTO().getName());
        Assert.assertEquals(result.getPriceDTO().getAmount(), reservationDTO.getPriceDTO().getAmount());
    }

    @Test
    public void getReservationById_invalidId() {
        Long id = 1L;

        when(reservationService.findById(any())).thenReturn(null);
        reservationFacade.getReservationById(1L);

        verify(reservationService, times(1)).findById(id);
    }

    @Test
    public void getAllReservations_valid() {
        Long firstId = 1L;
        Long secondId = 2L;

        Reservation firstReservation = getDefaultReservation();
        Reservation secondReservation = getDefaultReservation();

        firstReservation.setId(firstId);
        firstReservation.getTrip().setName("new");
        secondReservation.setId(secondId);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(firstReservation);
        reservations.add(secondReservation);

        ReservationDTO firstReservationDTO = getDefaultReservationDTO();
        ReservationDTO secondReservationDTO = getDefaultReservationDTO();

        firstReservationDTO.setId(firstId);
        firstReservationDTO.getTripDTO().setName("new");
        secondReservationDTO.setId(secondId);

        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        reservationsDTO.add(firstReservationDTO);
        reservationsDTO.add(secondReservationDTO);

        when(reservationService.findAll()).thenReturn(reservations);
        when(reservationService.findById(firstId)).thenReturn(firstReservation);
        when(reservationService.findById(secondId)).thenReturn(secondReservation);

        List<ReservationDTO> result = reservationFacade.getAllReservations();

        verify(reservationService, times(1)).findAll();
        Assert.assertEquals(result.get(0).getId(), reservationsDTO.get(0).getId());
        Assert.assertEquals(result.get(0).getTripDTO().getName(), reservationsDTO.get(0).getTripDTO().getName());
        Assert.assertEquals(result.get(1).getId(), reservationsDTO.get(1).getId());
        Assert.assertEquals(result.get(1).getTripDTO().getName(), reservationsDTO.get(1).getTripDTO().getName());
    }

    private static Reservation getDefaultReservation() {
        Reservation result = new Reservation();

        result.setTrip(getDefaultTrip());
        result.setCustomer(getDefaultCustomer());
        result.setPrice(getDefaultPrice());
        result.setExcursions(new HashSet<>());

        return result;
    }

    private static ReservationDTO getDefaultReservationDTO() {
        ReservationDTO result = new ReservationDTO();

        result.setTripDTO(getDefaultTripDTO());
        result.setCustomerDTO(getDefaultCustomerDTO());
        result.setPriceDTO(getDefaultPriceDTO());
        result.setExcursionsDTO(new HashSet<>());

        return result;
    }

    private static ReservationCreateDTO getDefaultReservationCreateDTO() {
        ReservationCreateDTO result = new ReservationCreateDTO();

        result.setTripDTO(getDefaultTripDTO());
        result.setCustomerDTO(getDefaultCustomerDTO());
        result.setPriceDTO(getDefaultPriceDTO());
        result.setExcursionsDTO(new HashSet<>());

        return result;
    }

    private static TripDTO getDefaultTripDTO() {
        TripDTO result = new TripDTO();

        result.setName("trip");
        result.setDestination("destination");
        result.setDateFrom(LocalDate.now().plusDays(1));
        result.setDateTo(LocalDate.now().plusDays(2));
        result.setAvailableTrips(0);

        return result;
    }

    private static CustomerDTO getDefaultCustomerDTO() {
        CustomerDTO result = new CustomerDTO();

        result.setName("customer");
        result.setSurname("surname");
        result.setBirthday(LocalDate.ofYearDay(1970, 1));
        result.setEmail("a@b.c");
        result.setAddress("address");
        result.setPhoneNumber(111111111L);

        return result;
    }

    private static PriceDTO getDefaultPriceDTO() {
        PriceDTO result = new PriceDTO();

        result.setAmount(BigDecimal.ONE);
        result.setValidFrom(LocalDate.now().plusDays(1));

        return result;
    }

    private static Trip getDefaultTrip() {
        Trip result = new Trip();

        result.setName("trip");
        result.setDestination("destination");
        result.setDateFrom(LocalDate.now().plusDays(1));
        result.setDateTo(LocalDate.now().plusDays(2));
        result.setAvailableTrips(0);

        return result;
    }

    private static Customer getDefaultCustomer() {
        Customer result = new Customer();

        result.setName("customer");
        result.setSurname("surname");
        result.setBirthday(LocalDate.ofYearDay(1970, 1));
        result.setEmail("a@b.c");
        result.setAddress("address");
        result.setPhoneNumber(111111111L);

        return result;
    }

    private static Price getDefaultPrice() {
        Price result = new Price();

        result.setAmount(BigDecimal.ONE);
        result.setValidFrom(LocalDate.now().plusDays(1));

        return result;
    }
}
