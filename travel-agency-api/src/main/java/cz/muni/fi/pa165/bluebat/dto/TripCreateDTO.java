package cz.muni.fi.pa165.bluebat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : Rudolf Madoran
 * @since : 27. 4. 2021, Tue
 **/
@Getter
@Setter
public class TripCreateDTO {

    @NotBlank
    private String name;

    @Future
    private LocalDate dateFrom;

    @Future
    private LocalDate dateTo;

    private String destination;

    @Positive
    private Integer availableTrips;

    private List<PriceCreateDTO> prices = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof TripCreateDTO)) return false;

        TripCreateDTO trip = (TripCreateDTO) obj;

        if (getDateFrom() != null ? !getDateFrom().equals(trip.getDateFrom()) : trip.getDateFrom() != null)
            return false;
        if (getDateTo() !=null ? !getDateTo().equals(trip.getDateTo()) : trip.getDateTo() != null)
            return false;
        if (getDestination() != null ? !getDestination().equals(trip.getDestination()) : trip.getDestination() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFrom,dateTo,destination);
    }
}
