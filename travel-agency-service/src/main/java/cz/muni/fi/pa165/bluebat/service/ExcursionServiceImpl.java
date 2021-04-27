package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.ExcursionDao;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
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
    public void create(Excursion excursion) {
        if (excursion == null) {
            throw new IllegalArgumentException("Excursion can not be null");
        }
        excursionDao.create(excursion);
    }

    @Override
    public void update(Excursion excursion) {
        if (excursion == null) {
            throw new IllegalArgumentException("Excursion can not be null");
        }
        Excursion previous = excursionDao.findById(excursion.getId());
        if (previous == null) {
            throw new IllegalArgumentException("Excursion has not been found in database");
        }
        priceService.updatePrices(previous.getPrices(), excursion.getPrices());
        excursionDao.update(excursion);
    }

    @Override
    public void delete(Excursion excursion) {
        excursionDao.delete(excursion);
    }

    @Override
    public Excursion findById(Long id) {
        return excursionDao.findById(id);
    }
}
