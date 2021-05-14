package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.ExcursionDao;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;
import cz.muni.fi.pa165.bluebat.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Tomáš Hampl on 3.3.21.
 */
@Service
@Transactional
public class ExcursionServiceImpl implements ExcursionService {

    private final ExcursionDao excursionDao;

    private final PriceService priceService;

    @Autowired
    public ExcursionServiceImpl(ExcursionDao excursionDao, PriceService priceService) {
        this.excursionDao = excursionDao;
        this.priceService = priceService;
    }

    @Override
    public void create(Excursion excursion, Trip trip) {
        try {
            excursion.setTrip(trip);
            excursionDao.create(excursion);
        } catch (Exception e) {
            throw new WrongDataAccessException("Excursion dao layer exception", e);
        }
    }

    @Override
    public void update(Excursion excursion) {
        Validator.NotNull(excursion, "Excursion");

        Excursion previous;
        try {
            previous = excursionDao.findById(excursion.getId());
        } catch (Exception e) {
            throw new WrongDataAccessException("Excursion dao layer exception", e);
        }
        Validator.Found(excursion, "Excursion");

        priceService.updatePrices(previous.getPrices(), excursion.getPrices());

        try {
            excursion.setTrip(previous.getTrip());
            excursionDao.update(excursion);
        } catch (Exception e) {
            throw new WrongDataAccessException("Excursion dao layer exception", e);
        }
    }

    @Override
    public void delete(Excursion excursion) {
        try {
            excursionDao.delete(excursion);
        } catch (Exception e) {
            throw new WrongDataAccessException("Excursion dao layer exception", e);
        }
    }

    @Override
    public Excursion findById(Long id) {
        try {
            return excursionDao.findById(id);
        } catch (Exception e) {
            throw new WrongDataAccessException("Excursion dao layer exception", e);
        }
    }
}
