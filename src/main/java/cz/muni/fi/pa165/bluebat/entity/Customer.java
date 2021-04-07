package cz.muni.fi.pa165.bluebat.entity;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.GenerationType;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Entiny for basic data about customer.
 *
 * @author : Rudolf Madoran
 * @since : 7. 4. 2021, Wed
 **/
@Entity
public class Customer {

    @OneToOne
    private Reservation reservation;

    @Id
    @Setter
    @Getter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDate birthday;

    @Getter
    @Setter
    @Column(nullable = false)
    private String name;

    @Getter
    @Setter
    @Column(nullable = false)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    private String address;

    @Getter
    @Setter
    @Column(nullable = false)
    private String surname;

    @Getter
    @Setter
    @Column(nullable = false)
    private Long phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (getBirthday() != null ? !getBirthday().equals(customer.getBirthday()) : customer.getBirthday() != null)
            return false;
        if (getName() != null ? !getName().equals(customer.getName()) : customer.getName() != null) return false;
        if (getEmail() != null ? !getEmail().equals(customer.getEmail()) : customer.getEmail() != null) return false;
        if (getAddress() != null ? !getAddress().equals(customer.getAddress()) : customer.getAddress() != null)
            return false;
        if (getSurname() != null ? !getSurname().equals(customer.getSurname()) : customer.getSurname() != null)
            return false;
        return getPhoneNumber() != null ? getPhoneNumber().equals(customer.getPhoneNumber()) : customer.getPhoneNumber() == null;
    }

    @Override
    public int hashCode() {
        int result = getBirthday() != null ? getBirthday().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        return result;
    }
}
