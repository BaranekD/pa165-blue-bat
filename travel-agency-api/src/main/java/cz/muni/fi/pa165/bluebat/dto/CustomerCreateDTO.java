package cz.muni.fi.pa165.bluebat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Ondrej Vaca
 */
@Getter
@Setter
public class CustomerCreateDTO {

    @NotNull
    @Past
    private LocalDate birthday;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String address;

    @NotBlank
    private String surname;

    @NotNull
    @Positive
    private Long phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerCreateDTO)) return false;
        CustomerCreateDTO customer = (CustomerCreateDTO) o;

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
        return Objects.hash(getBirthday(),getName(),getEmail(),getSurname(),getPhoneNumber(),getAddress());
    }
}
