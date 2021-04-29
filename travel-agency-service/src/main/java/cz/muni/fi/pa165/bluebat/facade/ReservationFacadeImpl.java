package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dto.ReservationCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.ReservationDTO;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Reservation;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReservationFacadeImpl { //implements ReservationFacade {

//    private final ReservationService reservationService;
//
//    @Autowired
//    public ReservationFacadeImpl(ReservationService rs) {
//        this.reservationService = rs;
//    }
//
//    @Override
//    public void createReservation(ReservationCreateDTO reservationDTO) {
//        Trip trip = mapTripDTOToTrip(reservationDTO.getTripDTO());
//        Customer customer = mapCustomerDTOToCustomer(reservationDTO.getCustomerDTO());
//        Price price = mapPriceDTOToPrice(reservationDTO.getPriceDTO());
//        Set<Excursion> excursions = mapExcursionsDTOToExcursions(reservationDTO.getExcursionsDTO());
//
//        Reservation reservation = mapReservationDTOToReservation(reservationDTO);
//        reservation.setTrip(trip);
//        reservation.setCustomer(customer);
//        reservation.setPrice(price);
//        reservation.setExcursions(excursions);
//
//        reservationService.create(reservation);
//    }
//
//    @Override
//    public void updateReservation(ReservationDTO reservationDTO) {
//        Reservation reservation = mapReservationDTOToReservation(reservationDTO);
//
//        reservationService.update(reservation);
//    }
//
//    @Override
//    public void deleteReservation(Long id) {
//        Reservation reservation = reservationService.findById(id);
//        reservationService.delete(reservation);
//    }
//
//    @Override
//    public ReservationDTO getReservationById(Long id) {
//        Reservation reservation = reservationService.findById(id);
//
//        if (reservation == null) {
//            return null;
//        }
//
//        ReservationDTO result = new ReservationDTO();
//        result.setId(reservation.getId());
//        result.setExcursionsDTO(mapExcursionsToExcursionsDTO(reservation.getExcursions()));
//        result.setTripDTO(mapTripToTripDTO(reservation.getTrip()));
//        result.setCustomerDTO(mapCustomerToCustomerDTO(reservation.getCustomer()));
//        result.setPriceDTO(mapPriceToPriceDTO(reservation.getPrice()));
//
//        return result;
//    }
//
//    @Override
//    public List<ReservationDTO> getAllReservations() {
//        List<Reservation> reservations = reservationService.findAll();
//        List<ReservationDTO> result = new ArrayList<>();
//
//        for (Reservation reservation : reservations) {
//            result.add(getReservationById(reservation.getId()));
//        }
//
//        return result;
//    }
}
