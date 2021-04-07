package cz.muni.fi.pa165.bluebat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Ondrej Vaca
 * Class representing a Trip entity
 */

@Entity
@Setter
@ToString
public class Trip {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    private String name;

    @Column(nullable = false)
    @Getter
    private LocalDate dateFrom;

    @Column(nullable = false)
    @Getter
    private LocalDate dateTo;

    @Column(nullable = false)
    @Getter
    private String destination;

    @Column(nullable = false)
    @Getter
    private int availableTrips;

    @OneToMany()
    @OrderBy("validFrom DESC")
    @JoinTable(name = "TRIP_PRICE")
    private List<Price> prices = new ArrayList<>();

    @OneToMany
    @JoinTable()
    private Set<Excursion> excursions;

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
