package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dao.ReservationDao;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Reservation;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class ReservationServiceImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private ReservationDao reservationDao;

    @Mock
    private PriceService priceService;

    private ReservationService reservationService;

    private Customer customer;
    private Trip trip;
    private final Set<Excursion> excursions = new HashSet<>();

    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
        reservationService = new ReservationServiceImpl(reservationDao, priceService);

        this.customer = getDefaultCustomer();
        this.trip = getDefaultTrip();

        doNothing().when(reservationDao).create(any());
        doNothing().when(reservationDao).update(any());
        doNothing().when(reservationDao).delete(any());
    }

    @Test
    public void create_valid() {
        Reservation reservation = getDefaultReservation();
        reservationService.create(reservation, this.customer, this.trip, this.excursions);

        verify(reservationDao, times(1)).create(reservation);
    }

    @Test
    public void create_nullReservation_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.create(null, this.customer, this.trip, this.excursions));
    }

    @Test
    public void create_nullCustomer_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.create(getDefaultReservation(), null, this.trip, this.excursions));
    }

    @Test
    public void create_nullTrip_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.create(getDefaultReservation(), this.customer, null, this.excursions));
    }

    @Test
    public void create_nullExcursions_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.create(getDefaultReservation(), this.customer, this.trip, null));
    }

    @Test
    public void create_daoThrowsException_WrongDataAccessException() {
        Reservation reservation = getDefaultReservation();
        doThrow(new IllegalArgumentException()).when(reservationDao).create(reservation);

        Assertions.assertThrows(WrongDataAccessException.class, () -> reservationService.create(reservation, this.customer, this.trip, this.excursions));
    }

    @Test
    public void update_called() {
        Reservation reservation = getDefaultInsertedReservation();
        when(reservationDao.findById(reservation.getId())).thenReturn(reservation);

        reservationService.update(reservation);
        verify(reservationDao, times(1)).update(reservation);
    }

    @Test
    public void update_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.update(null));
    }

    @Test
    public void update_notInserted_IllegalArgumentException() {
        Reservation reservation = getDefaultInsertedReservation();
        Long id = reservation.getId();

        when(reservationService.findById(id)).thenReturn(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> reservationService.update(reservation));
    }

    @Test
    public void update_daoThrowsException_WrongDataAccessException() {
        Reservation reservation = getDefaultReservation();
        when(reservationDao.findById(reservation.getId())).thenReturn(reservation);

        doThrow(new IllegalArgumentException()).when(reservationDao).update(reservation);
        Assertions.assertThrows(WrongDataAccessException.class, () -> reservationService.update(reservation));
    }

    @Test
    public void delete_called() {
        Reservation reservation = getDefaultInsertedReservation();
        when(reservationDao.findById(reservation.getId())).thenReturn(reservation);

        reservationService.delete(reservation);
        verify(reservationDao, times(1)).delete(reservation);
    }

    @Test
    public void delete_daoThrowsException_WrongDataAccessException() {
        Reservation reservation = getDefaultReservation();
        when(reservationDao.findById(reservation.getId())).thenReturn(reservation);

        doThrow(new IllegalArgumentException()).when(reservationDao).delete(reservation);
        Assertions.assertThrows(WrongDataAccessException.class, () -> reservationService.delete(reservation));
    }

    @Test
    public void getTotalReservationPrice_nullTrip_IllegalArgumentException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.getTotalReservationPrice(null, new HashSet<>())
        );
    }

    @Test
    public void getTotalReservationPrice_nullExcursions_IllegalArgumentException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.getTotalReservationPrice(new Trip(), null)
        );
    }

    @Test
    public void getTotalReservationPrice_emptyExcursions_valid() {
        Reservation reservation = getDefaultReservation();
        reservation.setTrip(this.trip);
        reservation.setExcursions(new HashSet<>());

        List<Price> prices = new ArrayList<>();
        prices.add(getDefaultPricePast());
        prices.add(getDefaultPriceFuture());

        reservation.getTrip().setPrices(prices);

        Assert.assertEquals(
                BigDecimal.TEN,
                reservationService.getTotalReservationPrice(reservation.getTrip(), reservation.getExcursions())
        );
    }

    @Test
    public void getTotalReservationPrice_valid() {
        Reservation reservation = getDefaultReservation();
        reservation.setTrip(this.trip);

        Set<Excursion> excursions = new HashSet<>();

        Excursion excursion1 = new Excursion();
        excursion1.setName("a");
        excursion1.addPrice(getDefaultPricePast());
        excursion1.addPrice(getDefaultPriceFuture());

        excursions.add(excursion1);

        Price examplePrice = new Price();
        examplePrice.setAmount(BigDecimal.valueOf(50));
        examplePrice.setValidFrom(LocalDate.now().minusDays(1));

        Excursion excursion2 = new Excursion();
        excursion2.setName("b");
        excursion2.addPrice(examplePrice);

        excursions.add(excursion2);
        reservation.setExcursions(excursions);

        List<Price> prices = new ArrayList<>();
        prices.add(getDefaultPricePast());
        prices.add(getDefaultPriceFuture());

        reservation.getTrip().setPrices(prices);

        Assert.assertEquals(
                BigDecimal.valueOf(70),
                reservationService.getTotalReservationPrice(reservation.getTrip(), reservation.getExcursions())
        );
    }

    private static Reservation getDefaultReservation() {
        return new Reservation();
    }

    private static Reservation getDefaultInsertedReservation() {
        Reservation result = getDefaultReservation();
        result.setId(1L);

        return result;
    }

    private static Trip getDefaultTrip() {
        Trip result = new Trip();

        result.setName("trip");
        result.setDestination("destination");
        result.setDateFrom(LocalDate.now().plusDays(1));
        result.setDateTo(LocalDate.now().plusDays(2));
        result.setAvailableTrips(0);
        result.setPrices(Collections.singletonList(getDefaultPricePast()));

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

    private static Price getDefaultPricePast() {
        Price result = new Price();

        result.setAmount(BigDecimal.TEN);
        result.setValidFrom(LocalDate.now().minusDays(1));

        return result;
    }

    private static Price getDefaultPriceFuture() {
        Price result = new Price();

        result.setAmount(BigDecimal.ONE);
        result.setValidFrom(LocalDate.now().plusDays(1));

        return result;
    }
}
