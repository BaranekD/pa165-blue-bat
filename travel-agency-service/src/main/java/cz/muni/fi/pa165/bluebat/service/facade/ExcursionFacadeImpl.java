package cz.muni.fi.pa165.bluebat.service.facade;

import cz.muni.fi.pa165.bluebat.dto.ExcursionCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.ExcursionDTO;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.facade.ExcursionFacade;
import cz.muni.fi.pa165.bluebat.service.BeanMappingService;
import cz.muni.fi.pa165.bluebat.service.ExcursionService;
import cz.muni.fi.pa165.bluebat.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ExcursionFacadeImpl implements ExcursionFacade {

    private final ExcursionService excursionService;
    private final TripService tripService;
    private final BeanMappingService beanMappingService;

    @Autowired
    public ExcursionFacadeImpl(ExcursionService excursionService, TripService tripService, BeanMappingService beanMappingService) {
        this.excursionService = excursionService;
        this.tripService = tripService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public void createExcursion(ExcursionCreateDTO excursionCreateDTO){
        Trip trip = tripService.findById(excursionCreateDTO.getParentId());
        if (trip == null) {
            throw new IllegalArgumentException("Trip has not been found");
        }

        Excursion excursion = beanMappingService.mapTo(excursionCreateDTO, Excursion.class);
        excursion.setTrip(trip);

        excursionService.create(excursion);
    }

    @Override
    public void updateExcursion(ExcursionDTO excursionDTO){
        Trip trip = tripService.findById(excursionDTO.getParentId());
        if (trip == null) {
            throw new IllegalArgumentException("Trip has not been found");
        }

        Excursion excursion = beanMappingService.mapTo(excursionDTO, Excursion.class);
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
        ExcursionDTO result = beanMappingService.mapTo(excursion, ExcursionDTO.class);
        result.setParentId(excursion.getTrip().getId());
        return result;
    }
}
