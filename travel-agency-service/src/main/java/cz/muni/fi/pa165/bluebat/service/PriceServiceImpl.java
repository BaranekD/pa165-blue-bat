package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.PriceDao;
import cz.muni.fi.pa165.bluebat.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Tomáš Hampl on 3.3.21.
 */
@Service
@Transactional
public class PriceServiceImpl implements PriceService{

    private final PriceDao priceDao;

    @Autowired
    public PriceServiceImpl(PriceDao priceDao) {
        this.priceDao = priceDao;
    }

    @Override
    public void create(Price price) {
        try {
            priceDao.create(price);
        } catch (Exception e) {
            throw new DataAccessException("Price dao layer exception", e) {  };
        }
    }

    @Override
    public void update(Price price) {
        if (price == null) {
            throw new IllegalArgumentException("Price can not be null");
        }

        Price previous;
        try {
            previous = priceDao.findById(price.getId());
        } catch (Exception e) {
            throw new DataAccessException("Price dao layer exception", e) {  };
        }
        if (previous == null) {
            throw new IllegalArgumentException("Price has not been found in database");
        }

        try {
            priceDao.update(price);
        } catch (Exception e) {
            throw new DataAccessException("Price dao layer exception", e) {  };
        }
    }

    @Override
    public void updatePrices(List<Price> oldPrices, List<Price> newPrices) {
        if (oldPrices == null) {
            throw new IllegalArgumentException("List of old prices can not be null");
        }
        if (newPrices == null) {
            throw new IllegalArgumentException("List of new prices can not be null");
        }
        List<Long> newPricesIds = newPrices.stream().map(Price::getId).filter(x -> x != null && x > 0).collect(Collectors.toList());
        List<Price> toDelete = oldPrices
                .stream().filter(x -> !newPricesIds.contains(x.getId())).collect(Collectors.toList());

        for (Price price: toDelete) {
            delete(price);
        }
        for (Price price: newPrices) {
            if (price.getId() != null && price.getId() > 0) update(price);
            else create(price);
        }
    }

    @Override
    public void delete(Price price) {
        try {
            priceDao.delete(price);
        } catch (Exception e) {
            throw new DataAccessException("Price dao layer exception", e) {  };
        }
    }
}
