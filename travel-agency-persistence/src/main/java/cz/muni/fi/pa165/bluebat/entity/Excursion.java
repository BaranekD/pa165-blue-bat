package cz.muni.fi.pa165.bluebat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Tomáš Hampl on 3.3.21.
 */
@Entity
public class Excursion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    @NotBlank
    private String name;

    @Setter
    @Getter
    @NotNull
    private LocalDate dateFrom;

    @Setter
    @Getter
    @NotNull
    private Duration duration;

    @Setter
    @Getter
    @NotBlank
    private String destination;

    @Setter
    @Getter
    private String description;

    @ManyToOne
    private Trip trip;

    @OneToMany
    @OrderBy("validFrom DESC")
    @JoinTable
    private final List<Price> prices = new ArrayList<>();

    public List<Price> getPrices() {
        return Collections.unmodifiableList(prices);
    }

    public void addPrice(Price price) {
        prices.add(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Excursion)) return false;

        Excursion excursion = (Excursion) o;

        if (getName() != null ? !getName().equals(excursion.getName()) : excursion.getName() != null)
            return false;
        if (getDateFrom() != null ? !getDateFrom().equals(excursion.getDateFrom()) : excursion.getDateFrom() != null)
            return false;
        if (getDuration() != null ? !getDuration().equals(excursion.getDuration()) : excursion.getDuration() != null)
            return false;
        return getDestination() != null ? getDestination().equals(excursion.getDestination()) : excursion.getDestination() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getDateFrom() != null ? getDateFrom().hashCode() : 0);
        result = 31 * result + (getDuration() != null ? getDuration().hashCode() : 0);
        result = 31 * result + (getDestination() != null ? getDestination().hashCode() : 0);
        return result;
    }
}
