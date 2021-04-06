package cz.muni.fi.pa165.bluebat.entity;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.GenerationType;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author rudolf
 */
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
    private LocalDate birthday;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private String surname;

    @Getter
    @Setter
    private Long phoneNumber;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getBirthday(), customer.getBirthday()) && Objects.equals(getName(), customer.getName()) && Objects.equals(getEmail(), customer.getEmail()) && Objects.equals(getAddress(), customer.getAddress()) && Objects.equals(getSurname(), customer.getSurname()) && Objects.equals(getPhoneNumber(), customer.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBirthday(), getName(), getEmail(), getAddress(), getSurname(), getPhoneNumber());
    }

}
