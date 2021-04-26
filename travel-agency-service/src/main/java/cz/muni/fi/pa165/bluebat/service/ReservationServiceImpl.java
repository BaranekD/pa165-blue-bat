package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.ReservationDao;
import cz.muni.fi.pa165.bluebat.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Dominik Baranek <460705@mail.muni.cz/>
 */
@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;

    @Autowired
    public ReservationServiceImpl(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public void create(Reservation r) {
        reservationDao.create(r);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationDao.findById(id);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    @Override
    public void update(Reservation r) {
        reservationDao.update(r);
    }

    @Override
    public void delete(Reservation r) {
        reservationDao.delete(findById(r.getId()));
    }
}
