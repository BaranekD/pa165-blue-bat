package cz.muni.fi.pa165.bluebat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @NotBlank
    private String name;

    @NotNull
    @Future
    private LocalDate dateFrom;

    @NotNull
    @Future
    private LocalDate dateTo;

    @NotBlank
    private String destination;

    @NotNull
    @Min(0)
    private Integer availableTrips;

    @OneToMany()
    @OrderBy("validFrom DESC")
    private List<Price> prices = new ArrayList<>();


    @OneToMany (mappedBy = "trip")
    private Set<Excursion> excursions = new HashSet<>();

    public Set<Excursion> getExcursions() {
        return Collections.unmodifiableSet(excursions);
    }

    public List<Price> getPrices() {
        return Collections.unmodifiableList(prices);
    }

    public void addExcursion(Excursion ex){
      excursions.add(ex);
    }
    public void removeExcursion(Excursion ex){
        excursions.remove(ex);
    }

    public void addPrice(Price price) {
        prices.add(price);
    }
    public void removePrice(Price price) {
        prices.remove(price);
    }

    public BigDecimal getPrice(){
        BigDecimal realPrice = new BigDecimal(0);
        for(Price price:prices)
        {
            if (LocalDate.now().compareTo(price.getValidFrom()) >= 0) {
                realPrice = price.getAmount();
            } else {
                break;
            }
        }
        return realPrice;
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
