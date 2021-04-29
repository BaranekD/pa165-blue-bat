package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.ReservationDao;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Reservation;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;
import cz.muni.fi.pa165.bluebat.utils.PriceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
        if (r == null) {
            throw new IllegalArgumentException("Reservation can not be null");
        }

        try {
            reservationDao.create(r);
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while creating a reservation", ex);
        }
    }

    @Override
    public Reservation findById(Long id) {
        try {
            return reservationDao.findById(id);
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while finding a reservation", ex);
        }
    }

    @Override
    public List<Reservation> findAll() {
        try {
            return reservationDao.findAll();
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while finding reservations", ex);
        }
    }

    @Override
    public void update(Reservation r) {
        if (r == null) {
            throw new IllegalArgumentException("Reservation can not be null");
        }

        Reservation previous = reservationDao.findById(r.getId());

        if (previous == null) {
            throw new IllegalArgumentException("Reservation has not been found in database");
        }

        try {
            reservationDao.update(r);
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while updating a reservation", ex);
        }
    }

    @Override
    public void delete(Reservation r) {
        try {
            reservationDao.delete(findById(r.getId()));
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while deleting a reservation", ex);
        }
    }

    @Override
    public BigDecimal getTotalReservationPrice(Trip trip, Set<Excursion> excursions) {
        if (trip == null) {
            throw new IllegalArgumentException("Trip can not be null");
        }

        if (excursions == null) {
            throw new IllegalArgumentException("Excursions can not be null");
        }

        BigDecimal sum = PriceUtils.getCurrentPrice(trip.getPrices());

        for (Excursion excursion : excursions) {
            sum = sum.add(PriceUtils.getCurrentPrice(excursion.getPrices()));
        }

        return sum;
    }
}
