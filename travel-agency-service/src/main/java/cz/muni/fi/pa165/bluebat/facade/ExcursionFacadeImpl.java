package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dto.ExcursionCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.ExcursionDTO;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.service.BeanMappingService;
import cz.muni.fi.pa165.bluebat.service.ExcursionService;
import cz.muni.fi.pa165.bluebat.service.TripService;
import cz.muni.fi.pa165.bluebat.utils.Validator;
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
    public ExcursionDTO createExcursion(ExcursionCreateDTO excursionCreateDTO){
        Validator.NotNull(excursionCreateDTO, "ExcursionCreateDTO");
        Trip trip = tripService.findById(excursionCreateDTO.getParentId());
        Validator.Found(trip, "Trip");

        Excursion excursion = beanMappingService.mapTo(excursionCreateDTO, Excursion.class);

        excursionService.create(excursion, trip);
        return beanMappingService.mapTo(excursion, ExcursionDTO.class);
    }

    @Override
    public ExcursionDTO updateExcursion(ExcursionDTO excursionDTO){
        Validator.NotNull(excursionDTO, "ExcursionDTO");
        Excursion excursion = beanMappingService.mapTo(excursionDTO, Excursion.class);

        excursionService.update(excursion);
        return beanMappingService.mapTo(excursion, ExcursionDTO.class);
    }

    @Override
    public void deleteExcursion(Long id){
        Validator.Positive(id, "Excursion id");
        Excursion excursion = excursionService.findById(id);
        excursionService.delete(excursion);
    }

    @Override
    public ExcursionDTO getExcursionById(Long id) {
        Validator.Positive(id, "Excursion id");
        Excursion excursion = excursionService.findById(id);
        if (excursion == null) {
            return null;
        }
        return beanMappingService.mapTo(excursion, ExcursionDTO.class);
    }
}
