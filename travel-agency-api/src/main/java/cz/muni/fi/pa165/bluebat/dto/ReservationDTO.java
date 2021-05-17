package cz.muni.fi.pa165.bluebat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Dominik Baranek <baranek@ics.muni.cz>
 */
@Getter
@Setter
public class ReservationDTO {

    private Long id;

    @NotNull
    private CustomerDTO customerDTO;

    @NotNull
    private TripDTO tripDTO;

    @NotNull
    private PriceDTO priceDTO;

    @NotNull
    private Set<ExcursionDTO> excursionsDTO;
}
