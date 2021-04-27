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

    @Autowired
    public ExcursionServiceImpl(ExcursionDao excursionDao) {
        this.excursionDao = excursionDao;
    }

    @Override
    public void create(Excursion excursion) {
        excursionDao.create(excursion);
    }

    @Override
    public void update(Excursion excursion) {
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
