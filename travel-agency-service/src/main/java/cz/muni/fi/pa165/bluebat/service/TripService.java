package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.entity.Trip;
import java.util.List;

/**
 * A service layer interface for the Trip entity.
 * @author Ondrej Vaca
 */
public interface TripService {

    /**
     * Creates a new trip
     *
     * @param trip Trip trip to be created
     */
    void create(Trip trip);

    /**
     * Updates a trip
     *
     * @param trip Trip trip to be updated
     */
    void update(Trip trip);

    /**
     * Remove a trip
     *
     * @param trip Trip trip to be removed
     */
    void delete(Trip trip);

    /**
     * Finds a trip
     *
     * @param id id of trip to be found
     * @return Trip when found, null otherwise
     */
    Trip findById(Long id);

    /**
     * Finds all trips
     *
     * @return List<Trip> list of all trips
     */
    List<Trip> findAll();

    /*
    /**
     * Add a price from trip
     *
     * @param trip Trip trip to be added to
     * @param price Price price to be added to trip


    public void addPrice(Trip trip, Price price);

    /**
     * Remove a price from trip
     *
     * @param trip Trip trip to be removed from
     * @param price Price price to be removed from trip

    public void removePrice(Trip trip, Price price);
    */
}
