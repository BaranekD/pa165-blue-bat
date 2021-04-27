package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.PriceDao;
import cz.muni.fi.pa165.bluebat.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        priceDao.create(price);
    }

    @Override
    public void update(Price price) {
        priceDao.update(price);
    }

    @Override
    public void delete(Price price) {
        priceDao.delete(price);
    }

    @Override
    public Price findById(Long id) {
        return priceDao.findById(id);
    }
}
