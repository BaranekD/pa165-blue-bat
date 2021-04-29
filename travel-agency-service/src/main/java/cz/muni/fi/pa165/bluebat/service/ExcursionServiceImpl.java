package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.ExcursionDao;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        try {
            excursionDao.create(excursion);
        } catch (Exception e) {
            throw new DataAccessException("Excursion dao layer exception", e) {  };
        }
    }

    @Override
    public void update(Excursion excursion) {
        if (excursion == null) {
            throw new IllegalArgumentException("Excursion can not be null");
        }

        Excursion previous;
        try {
            previous = excursionDao.findById(excursion.getId());
        } catch (Exception e) {
            throw new DataAccessException("Excursion dao layer exception", e) {  };
        }
        if (previous == null) {
            throw new IllegalArgumentException("Excursion has not been found in database");
        }

        priceService.updatePrices(previous.getPrices(), excursion.getPrices());

        try {
            excursionDao.update(excursion);
        } catch (Exception e) {
            throw new DataAccessException("Excursion dao layer exception", e) {  };
        }
    }

    @Override
    public void delete(Excursion excursion) {
        try {
            excursionDao.delete(excursion);
        } catch (Exception e) {
            throw new DataAccessException("Excursion dao layer exception", e) {  };
        }
    }

    @Override
    public Excursion findById(Long id) {
        try {
            return excursionDao.findById(id);
        } catch (Exception e) {
            throw new DataAccessException("Excursion dao layer exception", e) {  };
        }
    }
}
