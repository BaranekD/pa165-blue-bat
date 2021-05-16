package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.PriceDao;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;
import cz.muni.fi.pa165.bluebat.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new WrongDataAccessException("Price dao layer exception", e);
        }
    }

    @Override
    public void update(Price price) {
        Validator.NotNull(price, "Price");

        Price previous;
        try {
            previous = priceDao.findById(price.getId());
        } catch (Exception e) {
            throw new WrongDataAccessException("Price dao layer exception", e);
        }
        Validator.Found(previous, "Price");

        try {
            priceDao.update(price);
        } catch (Exception e) {
            throw new WrongDataAccessException("Price dao layer exception", e);
        }
    }

    @Override
    public void updatePrices(List<Price> oldPrices, List<Price> newPrices) {
        Validator.NotNull(oldPrices,"List of old prices");
        Validator.NotNull(newPrices,"List of new prices");

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
            throw new WrongDataAccessException("Price dao layer exception", e);
        }
    }
}
