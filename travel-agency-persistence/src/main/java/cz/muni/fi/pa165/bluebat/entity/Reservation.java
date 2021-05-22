package cz.muni.fi.pa165.bluebat.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class representing a Reservation entity
 *
 * @author Dominik Baranek <460705@mail.muni.cz/>
 */
@Entity
@Setter
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @NotNull
    private Customer customer;

    @OneToOne()
    @NotNull
    private Trip trip;

    @OneToOne
    @NotNull
    private Price price;

    @OneToMany()
    @JoinTable()
    private Set<Excursion> excursions = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;

        Reservation reservation = (Reservation) o;

        if (getCustomer() == null ? reservation.getCustomer() != null : !getCustomer().equals(reservation.getCustomer()))
            return false;

        if (getTrip() == null ? reservation.getTrip() != null : !getTrip().equals(reservation.getTrip()))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, trip);
    }
}
