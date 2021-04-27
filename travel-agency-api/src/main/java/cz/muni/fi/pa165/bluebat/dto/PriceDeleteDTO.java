package cz.muni.fi.pa165.bluebat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PriceDeleteDTO {
    @NotNull
    private Long parentId;

    @NotNull
    private Long priceId;
}
