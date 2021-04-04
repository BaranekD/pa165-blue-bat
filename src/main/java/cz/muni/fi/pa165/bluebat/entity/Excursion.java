package cz.muni.fi.pa165.bluebat.entity;

import javax.persistence.*;
import java.sql.Timestamp;
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
    private Long id;

    private String name;

    @Column(nullable = false)
    private LocalDate dateFrom;

    @Column(nullable = false)
    private Timestamp duration;

    private String destination;

    private String description;

    @OneToMany()
    @OrderBy("validFrom DESC")
    @JoinTable(name = "EXCURSION_PRICE")
    private List<Price> prices = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Timestamp getDuration() {
        return duration;
    }

    public void setDuration(Timestamp duration) {
        this.duration = duration;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

        if (getName() != null ? getName().equals(excursion.getName()) : excursion.getName() != null)
            return false;
        if (getDateFrom() != null ? getDateFrom().equals(excursion.getDateFrom()) : excursion.getDateFrom() != null)
            return false;
        if (getDuration() != null ? getDuration().equals(excursion.getDuration()) : excursion.getDuration() != null)
            return false;
        if (getDestination() != null ? getDestination().equals(excursion.getDestination()) : excursion.getDestination() != null)
            return false;
        if (getDescription() != null ? getDescription().equals(excursion.getDescription()) : excursion.getDescription() != null)
            return false;
        return true;
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
