package cz.muni.fi.pa165.bluebat.facade;
import cz.muni.fi.pa165.bluebat.dto.TripCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.TripDTO;

/**
 * @author : Rudolf Madoran
 * @since : 27. 4. 2021, Tue
 **/

public interface TripFacade {
    public void createTrip(TripCreateDTO dto);
    public void updateTrip(TripDTO dto);
    public void deleteTrip(Long tripId);
    TripDTO getTripDTO(Long id);
}
