package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dto.CustomerDTO;
import cz.muni.fi.pa165.bluebat.dto.ExcursionDTO;
import cz.muni.fi.pa165.bluebat.dto.PriceDTO;
import cz.muni.fi.pa165.bluebat.dto.ReservationCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.ReservationDTO;
import cz.muni.fi.pa165.bluebat.dto.TripDTO;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Reservation;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.facade.ReservationFacade;
import cz.muni.fi.pa165.bluebat.service.BeanMappingService;
import cz.muni.fi.pa165.bluebat.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class ReservationFacadeImpl implements ReservationFacade {

    private final ReservationService reservationService;
    private final BeanMappingService beanMappingService;

    @Autowired
    public ReservationFacadeImpl(ReservationService rs, BeanMappingService beanMappingService) {
        this.reservationService = rs;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public void createReservation(ReservationCreateDTO reservationDTO) {
        Trip trip = beanMappingService.mapTo(reservationDTO.getTripDTO(), Trip.class);
        Customer customer = beanMappingService.mapTo(reservationDTO.getCustomerDTO(), Customer.class);
        Price price = beanMappingService.mapTo(reservationDTO.getPriceDTO(), Price.class);
        List<Excursion> excursions = beanMappingService.mapTo(reservationDTO.getExcursionsDTO(), Excursion.class);

        Reservation reservation = new Reservation();

        reservation.setTrip(trip);
        reservation.setCustomer(customer);
        reservation.setPrice(price);
        reservation.setExcursions(new HashSet<>(excursions));

        reservationService.create(reservation);
    }

    @Override
    public void updateReservation(ReservationDTO reservationDTO) {
        Trip trip = beanMappingService.mapTo(reservationDTO.getTripDTO(), Trip.class);
        Customer customer = beanMappingService.mapTo(reservationDTO.getCustomerDTO(), Customer.class);
        Price price = beanMappingService.mapTo(reservationDTO.getPriceDTO(), Price.class);
        List<Excursion> excursions = beanMappingService.mapTo(reservationDTO.getExcursionsDTO(), Excursion.class);

        Reservation reservation = new Reservation();

        reservation.setId(reservationDTO.getId());
        reservation.setTrip(trip);
        reservation.setCustomer(customer);
        reservation.setPrice(price);
        reservation.setExcursions(new HashSet<>(excursions));

        reservationService.update(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationService.findById(id);
        reservationService.delete(reservation);
    }

    @Override
    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationService.findById(id);

        if (reservation == null) {
            return null;
        }

        TripDTO tripDTO = beanMappingService.mapTo(reservation.getTrip(), TripDTO.class);
        CustomerDTO customerDTO = beanMappingService.mapTo(reservation.getCustomer(), CustomerDTO.class);
        PriceDTO priceDTO = beanMappingService.mapTo(reservation.getPrice(), PriceDTO.class);
        List<ExcursionDTO> excursionsDTO = beanMappingService.mapTo(reservation.getExcursions(), ExcursionDTO.class);

        ReservationDTO result = new ReservationDTO();

        result.setId(reservation.getId());
        result.setTripDTO(tripDTO);
        result.setCustomerDTO(customerDTO);
        result.setPriceDTO(priceDTO);
        result.setExcursionsDTO(new HashSet<>(excursionsDTO));

        return result;
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        List<Reservation> reservations = reservationService.findAll();
        List<ReservationDTO> result = new ArrayList<>();

        for (Reservation reservation : reservations) {
            result.add(getReservationById(reservation.getId()));
        }

        return result;
    }
}
