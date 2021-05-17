package cz.muni.fi.pa165.bluebat.facade;
import cz.muni.fi.pa165.bluebat.dto.TripCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.TripDTO;

/**
 * @author : Rudolf Madoran
 * @since : 27. 4. 2021, Tue
 **/

public interface TripFacade {

    /**
     * Create a new Trip
     * @param dto is DTO TripCreateDTO used for creating Trip
     */
    void createTrip(TripCreateDTO dto);

    /**
     * Update a Trip
     * @param dto is DTO TripDTO used for updating Trip
     */
    void updateTrip(TripDTO dto);

    /**
     * Delete a Trip in system
     * @param tripId is id of Trip for delete
     */
    void deleteTrip(Long tripId);

    /**
     * Used for showing Trip from database
     * @param id is id of Trip for showing
     */
    TripDTO getTripDTO(Long id);
}
