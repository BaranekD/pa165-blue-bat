package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.entity.Reservation;

import java.util.List;

/**
 * An interface for manipulation with the Reservation database table.
 *
 * @author Dominik Baranek <460705@mail.muni.cz/>
 */
public interface ReservationDao {

    /**
     * Creates a new reservation in the database.
     *
     * @param r Reservation reservation to be created
     */
    void create(Reservation r);

    /**
     * Updates a reservation in the database
     *
     * @param r Reservation updated reservation
     */
    void update(Reservation r);

    /**
     * Finds a reservation in the database
     *
     * @param id Long id of reservation to be found
     * @return Reservation when found, null otherwise
     */
    Reservation findById(Long id);

    /**
     * Finds all reservations in the database
     *
     * @return List<Reservation> list of all reservations
     */
    List<Reservation> findAll();

    /**
     * Removes a reservation from the database
     *
     * @param r Reservation reservation to be removed
     */
    void delete(Reservation r);
}
