package cz.muni.fi.pa165.bluebat.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PriceDTO {
    private Long id;

    @Positive
    private BigDecimal amount;

    @NotNull
    private LocalDate validFrom;
}
