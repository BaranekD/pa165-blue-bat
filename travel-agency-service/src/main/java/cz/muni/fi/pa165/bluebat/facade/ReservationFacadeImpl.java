package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dto.ReservationCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.ReservationDTO;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Reservation;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.service.BeanMappingService;
import cz.muni.fi.pa165.bluebat.service.CustomerService;
import cz.muni.fi.pa165.bluebat.service.ExcursionService;
import cz.muni.fi.pa165.bluebat.service.ReservationService;
import cz.muni.fi.pa165.bluebat.service.TripService;
import cz.muni.fi.pa165.bluebat.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ReservationFacadeImpl implements ReservationFacade {

    private final ReservationService reservationService;
    private final CustomerService customerService;
    private final TripService tripService;
    private final ExcursionService excursionService;
    private final BeanMappingService beanMappingService;

    @Autowired
    public ReservationFacadeImpl(ReservationService rs, CustomerService cs, TripService ts, ExcursionService es, BeanMappingService beanMappingService) {
        this.reservationService = rs;
        this.customerService = cs;
        this.tripService = ts;
        this.excursionService = es;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public ReservationDTO createReservation(ReservationCreateDTO reservationCreateDTO) {
        Validator.NotNull(reservationCreateDTO, "ReservationCreateDTO");
        Validator.Positive(reservationCreateDTO.getCustomerId(), "Customer id");
        Validator.Positive(reservationCreateDTO.getTripId(), "Trip id");
        Validator.NotNull(reservationCreateDTO.getExcursionIds(), "Excursion ids");

        Customer customer = customerService.findCustomerById(reservationCreateDTO.getCustomerId());
        Validator.Found(customer, "Customer");

        Trip trip = tripService.findById(reservationCreateDTO.getTripId());
        Validator.Found(trip, "Trip");

        Set<Excursion> excursions = new HashSet<>();

        for (Long excursionId : reservationCreateDTO.getExcursionIds()) {
            Excursion e = excursionService.findById(excursionId);

            if (e != null) {
                excursions.add(e);
            }
        }

        Reservation reservation = beanMappingService.mapTo(reservationCreateDTO, Reservation.class);
        reservationService.create(reservation, customer, trip, excursions);

        return beanMappingService.mapTo(reservation, ReservationDTO.class);
    }

    @Override
    public ReservationDTO updateReservation(ReservationDTO reservationDTO) {
        Validator.NotNull(reservationDTO, "ReservationDTO");
        Reservation reservation = beanMappingService.mapTo(reservationDTO, Reservation.class);

        reservationService.update(reservation);

        return beanMappingService.mapTo(reservation, ReservationDTO.class);
    }

    @Override
    public void deleteReservation(Long id) {
        Validator.Positive(id, "Reservation id");
        Reservation reservation = reservationService.findById(id);
        reservationService.delete(reservation);
    }

    @Override
    public ReservationDTO getReservationById(Long id) {
        Validator.Positive(id, "Reservation id");
        Reservation reservation = reservationService.findById(id);
        return reservation == null ? null : beanMappingService.mapTo(reservation, ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        return beanMappingService.mapTo(reservationService.findAll(), ReservationDTO.class);
    }
}
