package cz.muni.fi.pa165.bluebat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
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

    @OneToOne
    @JoinColumn()
    @Getter
    private Price price;

    @OneToMany
    @JoinTable()
    private Set<Excursion> excursions;

    public Set<Excursion> getExcursions() {
        return Collections.unmodifiableSet(excursions);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Trip)) return false;
        Trip trip = (Trip) obj;
        return dateFrom.equals(trip.getDateFrom())&&dateTo.equals(trip.getDateFrom())&&destination.equals(trip.getDestination());
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateFrom,dateTo,destination);
    }
}
