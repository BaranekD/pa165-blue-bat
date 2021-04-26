package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.entity.Reservation;

import java.util.List;

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
    void create(Reservation r);

    /**
     * Finds a reservation by id
     *
     * @param id Long id of reservation to be found
     * @return Reservation when found, null otherwise
     */
    Reservation findById(Long id);

    /**
     * Finds all reservations
     *
     * @return List<Reservation> list of all reservations
     */
    List<Reservation> findAll();

    /**
     * Updates a reservation
     *
     * @param r Reservation updated version of reservation
     */
    void update(Reservation r);

    /**
     * Removes a reservation
     *
     * @param r Reservation reservation to be removed
     */
    void delete(Reservation r);
}
