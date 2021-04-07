package cz.muni.fi.pa165.bluebat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Class representing a Reservation entity
 *
 * @author Dominik Baranek <460705@mail.muni.cz/>
 */
@Entity
@ToString
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    @ToString.Exclude
    private Long id;

    @OneToOne
    @Getter
    @Column(nullable = false)
    private Customer customer;

    @OneToOne()
    @Setter
    @Getter
    //@JoinTable()
    //@Column(nullable = false)
    private Trip trip;

    @OneToOne
    @Setter
    @Getter
    //@Column(nullable = false)
    private Price price;

    @OneToMany()
    @JoinTable()
    @Setter
    private Set<Excursion> excursions;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.customer.setReservation(this);
    }

    public Set<Excursion> getExcursions() {
        return Collections.unmodifiableSet(excursions);
    }

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
