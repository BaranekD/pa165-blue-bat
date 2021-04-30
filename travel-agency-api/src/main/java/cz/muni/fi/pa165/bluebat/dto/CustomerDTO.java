package cz.muni.fi.pa165.bluebat.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * @author Ondrej Vaca
 */

@Getter
@Setter
public class CustomerDTO {

    private Long id;

    private LocalDate birthday;

    private String name;

    private String email;

    private String address;

    private String surname;

    private Long phoneNumber;
}
