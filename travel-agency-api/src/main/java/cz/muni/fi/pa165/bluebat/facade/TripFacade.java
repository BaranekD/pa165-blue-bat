package cz.muni.fi.pa165.bluebat.facade;
import cz.muni.fi.pa165.bluebat.dto.TripCreateDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author : Rudolf Madoran
 * @since : 27. 4. 2021, Tue
 **/

@Service
@Transactional
public interface TripFacade {
    public void createTrip(TripCreateDTO dto);
    public void updateTrip(Long tripId, TripCreateDTO dto);
    public void addExcursion(Long tripId, Long excursionId);
    public void removeExcursion(Long tripId, Long excursionId);
    public void addPrice(Long tripId, Long priceId);
    public void removePrice(Long tripId, Long priceId);
    public void deleteTrip(Long tripId);

}
