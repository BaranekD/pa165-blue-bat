package cz.muni.fi.pa165.bluebat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ExcursionCreateDTO {
    @Positive
    private Long parentId;

    @NotBlank
    private String name;

    @NotNull
    private LocalDate dateFrom;

    @NotNull
    private Duration duration;

    @NotBlank
    private String destination;

    private String description;

    private List<PriceCreateDTO> prices = new ArrayList<>();
}
