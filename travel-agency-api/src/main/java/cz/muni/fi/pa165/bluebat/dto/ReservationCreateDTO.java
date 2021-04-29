package cz.muni.fi.pa165.bluebat.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ReservationCreateDTO {

    private CustomerDTO customerDTO;

    private TripDTO tripDTO;

    private PriceDTO priceDTO;

    private Set<ExcursionDTO> excursionsDTO;
}
