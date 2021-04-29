package cz.muni.fi.pa165.bluebat.service.facade;

import cz.muni.fi.pa165.bluebat.dto.ExcursionDTO;
import cz.muni.fi.pa165.bluebat.dto.PriceDTO;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.facade.ExcursionFacade;
import cz.muni.fi.pa165.bluebat.service.ExcursionService;
import cz.muni.fi.pa165.bluebat.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExcursionFacadeImpl implements ExcursionFacade {

    private final ExcursionService excursionService;
    private final TripService tripService;

    @Autowired
    public ExcursionFacadeImpl(ExcursionService excursionService, TripService tripService) {
        this.excursionService = excursionService;
        this.tripService = tripService;
    }

    @Override
    public void createExcursion(ExcursionDTO excursionDTO){
        Trip trip = tripService.findById(excursionDTO.getParentId());
        if (trip == null) {
            throw new IllegalArgumentException("Trip has not been found");
        }

        Excursion excursion = getExcursion(excursionDTO);
        excursion.setTrip(trip);
        excursionService.create(excursion);
    }

    @Override
    public void updateExcursion(ExcursionDTO excursionDTO){
        Trip trip = tripService.findById(excursionDTO.getParentId());
        if (trip == null) {
            throw new IllegalArgumentException("Trip has not been found");
        }

        Excursion excursion = getExcursion(excursionDTO);
        excursion.setTrip(trip);

        excursionService.update(excursion);
    }

    @Override
    public void deleteExcursion(Long id){
        Excursion excursion = excursionService.findById(id);
        excursionService.delete(excursion);
    }

    @Override
    public ExcursionDTO getExcursionById(Long id) {
        Excursion excursion = excursionService.findById(id);
        if (excursion == null) {
            return null;
        }
        ExcursionDTO result = new ExcursionDTO();
        result.setId(excursion.getId());
        result.setName(excursion.getName());
        result.setDateFrom(excursion.getDateFrom());
        result.setDuration(excursion.getDuration());
        result.setDestination(excursion.getDestination());
        result.setDescription(excursion.getDescription());
        result.setPrices(excursion.getPrices().stream().map(this::getPriceDTO).collect(Collectors.toList()));
        result.setParentId(excursion.getTrip().getId());
        return result;
    }

    private Excursion getExcursion(ExcursionDTO excursionDTO) {
        Excursion result = new Excursion();
        result.setId(excursionDTO.getId());
        result.setName(excursionDTO.getName());
        result.setDateFrom(excursionDTO.getDateFrom());
        result.setDuration(excursionDTO.getDuration());
        result.setDestination(excursionDTO.getDestination());
        result.setDescription(excursionDTO.getDescription());
        result.setPrices(excursionDTO.getPrices().stream().map(this::getPrice).collect(Collectors.toList()));
        return result;
    }

    private Price getPrice(PriceDTO priceDTO) {
        Price result = new Price();
        result.setId(priceDTO.getId());
        result.setAmount(priceDTO.getAmount());
        result.setValidFrom(priceDTO.getValidFrom());
        return result;
    }

    private PriceDTO getPriceDTO(Price price) {
        PriceDTO result = new PriceDTO();
        result.setId(price.getId());
        result.setAmount(price.getAmount());
        result.setValidFrom(price.getValidFrom());
        return result;
    }
}
