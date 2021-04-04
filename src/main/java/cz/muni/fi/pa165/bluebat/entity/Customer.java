package cz.muni.fi.pa165.bluebat.entity;/* created by rudolf */


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author rudolf
 */
@Entity
public class Customer {
    public Customer() {

    }

    public Customer(String number) {

        this.number = number;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate birthday;


    private String name;

    @NotNull
    @Column(nullable = false, unique = true)
    private String number;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return getNumber().equals(customer.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber());
    }
}