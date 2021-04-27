package cz.muni.fi.pa165.bluebat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PriceUpdateDTO {
    @NotNull
    private Long parentId;

    @NotNull
    private Long priceId;

    @Positive
    private BigDecimal amount;

    @NotNull
    private LocalDate validFrom;
}
