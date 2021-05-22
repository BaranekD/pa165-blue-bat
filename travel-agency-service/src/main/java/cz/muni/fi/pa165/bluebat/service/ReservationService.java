package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Reservation;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * A service layer interface for the Reservation entity.
 *
 * @author Dominik Baranek <460705@mail.muni.cz/>
 */
public interface ReservationService {

    /**
     * Creates a new reservation
     *
     * @param r Reservation reservation to be created
     */
    void create(Reservation r, Customer c, Trip t, Set<Excursion> e) throws WrongDataAccessException;

    /**
     * Finds a reservation by id
     *
     * @param id Long id of reservation to be found
     * @return Reservation when found, null otherwise
     */
    Reservation findById(Long id) throws WrongDataAccessException;

    /**
     * Finds all reservations
     *
     * @return List<Reservation> list of all reservations
     */
    List<Reservation> findAll() throws WrongDataAccessException;

    /**
     * Updates a reservation
     *
     * @param r Reservation updated version of reservation
     */
    void update(Reservation r) throws WrongDataAccessException;

    /**
     * Removes a reservation
     *
     * @param r Reservation reservation to be removed
     */
    void delete(Reservation r) throws WrongDataAccessException;

    /**
     * Computes a sum of a trip and all chosen excursions
     *
     * @param trip Trip booked trip
     * @param excursions Set<Excursion> list of booked excursions
     * @return BigDecimal total price of given trip and excursions
     */
    BigDecimal getTotalReservationPrice(Trip trip, Set<Excursion> excursions);
}
