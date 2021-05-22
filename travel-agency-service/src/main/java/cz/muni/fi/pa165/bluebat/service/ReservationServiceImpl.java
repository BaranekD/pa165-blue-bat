package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.ReservationDao;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.entity.Reservation;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;
import cz.muni.fi.pa165.bluebat.utils.PriceUtils;
import cz.muni.fi.pa165.bluebat.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author Dominik Baranek <460705@mail.muni.cz/>
 */
@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;
    private final PriceService priceService;

    @Autowired
    public ReservationServiceImpl(ReservationDao reservationDao, PriceService priceService) {
        this.reservationDao = reservationDao;
        this.priceService = priceService;
    }

    @Override
    public void create(Reservation r, Customer c, Trip t, Set<Excursion> e) {
        Validator.NotNull(r, "Reservation");
        Validator.NotNull(c, "Reservation");
        Validator.NotNull(t, "Reservation");
        Validator.NotNull(e, "Reservation");

        r.setCustomer(c);
        r.setTrip(t);
        r.setExcursions(e);

        Price price = new Price();
        price.setValidFrom(LocalDate.now());
        price.setAmount(getTotalReservationPrice(t, e));
        priceService.create(price);

        r.setPrice(price);

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
        Validator.NotNull(r, "Reservation");
        Reservation previous = reservationDao.findById(r.getId());
        Validator.Found(previous, "Reservation");

        try {
            reservationDao.update(r);
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while updating a reservation", ex);
        }
    }

    @Override
    public void delete(Reservation r) {
        try {
            priceService.delete(r.getPrice());
            reservationDao.delete(r);
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while deleting a reservation", ex);
        }
    }

    @Override
    public BigDecimal getTotalReservationPrice(Trip trip, Set<Excursion> excursions) {
        Validator.NotNull(trip, "Trip");
        Validator.NotNull(excursions, "Excursions");

        BigDecimal sum = PriceUtils.getCurrentPrice(trip.getPrices());

        for (Excursion excursion : excursions) {
            sum = sum.add(PriceUtils.getCurrentPrice(excursion.getPrices()));
        }

        return sum;
    }
}
