package cz.muni.fi.pa165.bluebat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Dominik Baranek <baranek@ics.muni.cz>
 */
@Getter
@Setter
public class ReservationDTO {

    private Long id;

    @NotNull
    private CustomerDTO customer;

    @NotNull
    private TripDTO trip;

    @NotNull
    private PriceDTO price;

    private Set<ExcursionDTO> excursions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationDTO)) return false;
        ReservationDTO reservation = (ReservationDTO) o;

        if (getId() != null ?!getId().equals(reservation.getId()) : reservation.getId() != null)
            return false;
        if (getCustomer() != null ? !getCustomer().equals(reservation.getCustomer()) : reservation.getCustomer() != null)
            return false;
        if (getTrip() != null ? !getTrip().equals(reservation.getTrip()) : reservation.getTrip() != null)
            return false;
        if (getPrice() != null ? !getPrice().equals(reservation.getPrice()) : reservation.getPrice() != null)
            return false;
        return Objects.equals(excursions, reservation.getExcursions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, trip, price, excursions);
    }
}
