package cz.muni.fi.pa165.bluebat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Dominik Baranek <baranek@ics.muni.cz>
 */
@Getter
@Setter
public class ReservationCreateDTO {

    @Positive
    private Long customerId;

    @Positive
    private Long tripId;

    private Set<Long> excursionIds = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationCreateDTO)) return false;
        ReservationCreateDTO reservation = (ReservationCreateDTO) o;

        if (getCustomerId() != null ? !getCustomerId().equals(reservation.getCustomerId()) : reservation.getCustomerId() != null)
            return false;
        if (getTripId() != null ? !getTripId().equals(reservation.getTripId()) : reservation.getTripId() != null)
            return false;
        return Objects.equals(excursionIds, reservation.getExcursionIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, tripId, excursionIds);
    }
}
