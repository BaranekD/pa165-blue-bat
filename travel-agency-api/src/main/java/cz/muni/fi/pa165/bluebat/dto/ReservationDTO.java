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
    private CustomerDTO customerDTO;

    @NotNull
    private TripDTO tripDTO;

    private Set<ExcursionDTO> excursionDTOs = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationDTO)) return false;
        ReservationDTO reservation = (ReservationDTO) o;

        if (getId() != null ?!getId().equals(reservation.getId()) : reservation.getId() != null)
            return false;
        if (getCustomerDTO() != null ? !getCustomerDTO().equals(reservation.getCustomerDTO()) : reservation.getCustomerDTO() != null)
            return false;
        if (getTripDTO() != null ? !getTripDTO().equals(reservation.getTripDTO()) : reservation.getTripDTO() != null)
            return false;
        return Objects.equals(excursionDTOs, reservation.getExcursionDTOs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerDTO, tripDTO, excursionDTOs);
    }
}
