package cz.muni.fi.pa165.bluebat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Tomáš Hampl on 27.4.21.
 */
@Getter
@Setter
public class PriceCreateDTO {
    @Positive
    private BigDecimal amount;

    @NotNull
    private LocalDate validFrom;
}
