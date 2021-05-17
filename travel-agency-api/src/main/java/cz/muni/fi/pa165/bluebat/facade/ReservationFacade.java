package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dto.ReservationCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.ReservationDTO;

import java.util.List;

/**
 * A facade layer interface for manipulating with Excursion.
 *
 * @author Dominik Baranek <baranek@ics.muni.cz>
 */
public interface ReservationFacade {

    /**
     * Creates a reservation
     *
     * @param reservationDTO ReservationCreateDTO reservation to be created
     */
    void createReservation(ReservationCreateDTO reservationDTO);

    /**
     * Updates a reservation
     *
     * @param reservationDTO ReservationDTO reservation to be updated
     */
    void updateReservation(ReservationDTO reservationDTO);

    /**
     * Deletes a reservation
     *
     * @param id Long id of reservation to be deleted
     */
    void deleteReservation(Long id);

    /**
     * Finds a reservation
     *
     * @param id Long id of reservation to be found
     * @return ReservationDTO reservation
     */
    ReservationDTO getReservationById(Long id);

    /**
     * Finds all reservations
     *
     * @return List<ReservationDTO> list of all reservations
     */
    List<ReservationDTO> getAllReservations();
}
