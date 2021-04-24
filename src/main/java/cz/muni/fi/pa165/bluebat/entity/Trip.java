package cz.muni.fi.pa165.bluebat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Ondrej Vaca
 * Class representing a Trip entity
 */

@Entity
@Setter
@Getter
public class Trip {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    // Blank checks if not null or blank
    // Column generates database column with notnull
    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotNull
    @Future
    private LocalDate dateFrom;

    @Column(nullable = false)
    @NotNull
    @Future
    private LocalDate dateTo;

    @Column(nullable = false)
    @NotBlank
    private String destination;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer availableTrips;

    @OneToMany()
    @OrderBy("validFrom DESC")
    @JoinTable(name = "TRIP_PRICE")
    private List<Price> prices = new ArrayList<>();

    @OneToMany
    @JoinTable()
    private Set<Excursion> excursions = new HashSet<>();

    public Set<Excursion> getExcursions() {
        return Collections.unmodifiableSet(excursions);
    }

    public List<Price> getPrices() {
        return Collections.unmodifiableList(prices);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Trip)) return false;

        Trip trip = (Trip) obj;

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
