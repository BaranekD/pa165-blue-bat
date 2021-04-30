package cz.muni.fi.pa165.bluebat.dto;

import cz.muni.fi.pa165.bluebat.entity.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerCreateDTO {

    private Reservation reservation;

    private LocalDate birthday;

    private String name;

    private String email;

    private String address;

    private String surname;

    private Long phoneNumber;
}
