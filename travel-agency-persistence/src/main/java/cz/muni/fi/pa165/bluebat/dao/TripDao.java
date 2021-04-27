package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.entity.Trip;

import java.util.List;

/**
 * @author Ondrej Vaca
 * An interface for manipulation with the Trip database table.
 */
public interface TripDao {

    /**
     * Creates a new trip record in the database.
     *
     * @param trip Trip trip record to be created
     */
    void create(Trip trip);

    /**
     * Updates a trip record in the database
     *
     * @param trip Trip trip record to be updated
     */
    void update(Trip trip);

    /**
     * Remove a trip record from the database
     *
     * @param trip Trip trip record to be removed
     */
    void delete(Trip trip);

    /**
     * Finds a trip record in the database
     *
     * @param id id of trip record to be found
     * @return Trip when found, null otherwise
     */
    Trip findById(Long id);

    /**
     * Finds all trip records in the database
     *
     * @return List<Trip> list of all trips
     */
    List<Trip> findAll();
}
