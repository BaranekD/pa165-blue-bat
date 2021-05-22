package cz.muni.fi.pa165.bluebat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.*;

/**
 * @author : Rudolf Madoran
 * @since : 27. 4. 2021, Tue
 **/
@Getter
@Setter
public class TripDTO {

    private Long id;

    @NotBlank
    private String name;

    @Future
    private LocalDate dateFrom;

    @Future
    private LocalDate dateTo;

    private String destination;

    @Positive
    private Integer availableTrips;

    private List<PriceDTO> prices = new ArrayList<>();

    private List<ExcursionDTO> excursions = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof TripDTO)) return false;

        TripDTO trip = (TripDTO) obj;

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
