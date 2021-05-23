package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dto.CustomerDTO;
import cz.muni.fi.pa165.bluebat.dto.ReservationCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.ReservationDTO;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Reservation;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.service.BeanMappingService;
import cz.muni.fi.pa165.bluebat.service.CustomerService;
import cz.muni.fi.pa165.bluebat.service.ExcursionService;
import cz.muni.fi.pa165.bluebat.service.ReservationService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class ReservationFacadeImplTest extends AbstractTestNGSpringContextTests {

    private static final Long RESERVATION_ID = 3L;

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private ReservationService reservationService;

    @Mock
    private CustomerService customerService;

    @Mock
    private TripService tripService;

    @Mock
    private ExcursionService excursionService;

    private ReservationFacade reservationFacade;

    Reservation reservation;
    Reservation insertedReservation;
    ReservationDTO reservationDTO;
    ReservationCreateDTO reservationCreateDTO;

    Customer customer;
    Trip trip;
    Price price;

    Customer customer2;

    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
        reservationFacade = new ReservationFacadeImpl(reservationService, customerService, tripService, excursionService, beanMappingService);

        reservation = getDefaultReservation();
        insertedReservation = getDefaultInsertedReservation();
        reservationDTO = getDefaultReservationDTO();
        reservationCreateDTO = getDefaultReservationCreateDTO();

        customer = getDefaultCustomer();
        trip = getDefaultTrip();
        price = getDefaultPrice();

        customer2 = getDefaultCustomer2();

        when(beanMappingService.mapTo(getDefaultInsertedReservation(), ReservationDTO.class)).thenReturn(reservationDTO);
        when(beanMappingService.mapTo(getDefaultReservationDTO(), Reservation.class)).thenReturn(insertedReservation);
        when(beanMappingService.mapTo(getDefaultReservationCreateDTO(), Reservation.class)).thenReturn(reservation);

        doAnswer(invocation -> {
            ((Reservation) invocation.getArgument(0)).setId(RESERVATION_ID);
            ((Reservation) invocation.getArgument(0)).setCustomer(customer);
            ((Reservation) invocation.getArgument(0)).setTrip(trip);
            ((Reservation) invocation.getArgument(0)).setPrice(price);
            return null;
        }).when(reservationService).create(any(), any(), any(), any());

        doAnswer(invocation -> {
            ((Reservation) invocation.getArgument(0)).setCustomer(customer2);
            return null;
        }).when(reservationService).update(any());
    }

    @Test
    public void createReservation_valid() {
        when(reservationService.findById(RESERVATION_ID)).thenReturn(reservation);
        when(customerService.findCustomerById(1L)).thenReturn(customer);
        when(tripService.findById(1L)).thenReturn(trip);

        ReservationDTO result = reservationFacade.createReservation(reservationCreateDTO);

        verify(reservationService, times(1)).create(any(), any(), any(), any());
        Assert.assertEquals(result, getDefaultReservationDTO());
    }

    @Test
    public void createReservation_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationFacade.createReservation(null));
    }

    @Test
    public void updateReservation_valid() {
        ReservationDTO updatedReservationDTO = getDefaultReservationDTO();
        updatedReservationDTO.setCustomer(getDefaultCustomerDTO());

        Reservation updatedReservation = getDefaultInsertedReservation();
        updatedReservation.setCustomer(customer2);

        when(beanMappingService.mapTo(updatedReservation, ReservationDTO.class)).thenReturn(updatedReservationDTO);

        ReservationDTO result = reservationFacade.updateReservation(reservationDTO);

        verify(reservationService, times(1)).update(any());
        Assert.assertEquals(result, updatedReservationDTO);
    }

    @Test
    public void updateReservation_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationFacade.updateReservation(null));
    }

    @Test
    public void deleteReservation_valid() {
        when(reservationService.findById(RESERVATION_ID)).thenReturn(reservation);
        reservationFacade.deleteReservation(RESERVATION_ID);

        verify(reservationService, times(1)).delete(reservation);
    }

    @Test
    public void deleteReservation_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationFacade.deleteReservation(null));
    }

    @Test
    public void deleteReservation_negative_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationFacade.deleteReservation(-1L));
    }

    @Test
    public void getReservationById_valid() {
        when(reservationService.findById(RESERVATION_ID)).thenReturn(getDefaultInsertedReservation());

        ReservationDTO result = reservationFacade.getReservationById(RESERVATION_ID);

        verify(reservationService, times(1)).findById(RESERVATION_ID);
        Assert.assertEquals(result, reservationDTO);
    }

    @Test
    public void getReservationById_invalidId() {
        Long id = 1L;

        when(reservationService.findById(any())).thenReturn(null);
        reservationFacade.getReservationById(1L);

        verify(reservationService, times(1)).findById(id);
    }

    @Test
    public void getReservationById_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationFacade.getReservationById(null));
    }

    @Test
    public void getReservationById_negative_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationFacade.getReservationById(-1L));
    }

    @Test
    public void getAllReservations_valid() {
        Reservation reservation = getDefaultReservation();
        reservation.setId(1L);
        reservation.setTrip(trip);
        reservation.setCustomer(customer);
        reservation.setPrice(price);
        reservation.setExcursions(new HashSet<>());

        Reservation secondReservation = getDefaultReservation();
        secondReservation.setId(5L);
        secondReservation.setTrip(trip);
        secondReservation.setCustomer(customer);
        secondReservation.setPrice(price);
        secondReservation.setExcursions(new HashSet<>());

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        reservations.add(secondReservation);

        ReservationDTO reservationDTO = getDefaultReservationDTO();
        reservationDTO.setId(1L);

        ReservationDTO secondReservationDTO = getDefaultReservationDTO();
        secondReservationDTO.setId(5L);

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        reservationDTOs.add(reservationDTO);
        reservationDTOs.add(secondReservationDTO);

        when(reservationService.findAll()).thenReturn(reservations);
        when(beanMappingService.mapTo(reservations, ReservationDTO.class)).thenReturn(reservationDTOs);
        List<ReservationDTO> result = reservationFacade.getAllReservations();

        verify(reservationService, times(1)).findAll();
        Assert.assertEquals(reservationDTOs, result);
    }

    private static Reservation getDefaultReservation() {
        return new Reservation();
    }

    private static Reservation getDefaultInsertedReservation() {
        Reservation reservation = getDefaultReservation();

        reservation.setId(RESERVATION_ID);
        reservation.setTrip(getDefaultTrip());
        reservation.setCustomer(getDefaultCustomer());
        reservation.setPrice(getDefaultPrice());
        reservation.setExcursions(new HashSet<>());

        return reservation;
    }

    private static ReservationDTO getDefaultReservationDTO() {
        ReservationDTO result = new ReservationDTO();
        result.setId(RESERVATION_ID);

        return result;
    }

    private static ReservationCreateDTO getDefaultReservationCreateDTO() {
        ReservationCreateDTO reservationCreateDTO = new ReservationCreateDTO();
        reservationCreateDTO.setCustomerId(1L);
        reservationCreateDTO.setTripId(1L);

        return reservationCreateDTO;
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

    private static Customer getDefaultCustomer2() {
        Customer result = getDefaultCustomer();
        result.setName("Changed Name");

        return result;
    }

    private static Price getDefaultPrice() {
        Price result = new Price();

        result.setAmount(BigDecimal.ONE);
        result.setValidFrom(LocalDate.now().plusDays(1));

        return result;
    }

    private static CustomerDTO getDefaultCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setName("Changed name");
        customerDTO.setSurname("surname");
        customerDTO.setBirthday(LocalDate.ofYearDay(1970, 1));
        customerDTO.setEmail("a@b.c");
        customerDTO.setAddress("address");
        customerDTO.setPhoneNumber(111111111L);

        return customerDTO;
    }
}
