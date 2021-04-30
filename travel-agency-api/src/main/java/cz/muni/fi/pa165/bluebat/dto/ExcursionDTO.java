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

/**
 * Created by Tomáš Hampl on 27.4.21.
 */
@Getter
@Setter
public class ExcursionDTO {
    @Positive
    private Long parentId;

    @Positive
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private LocalDate dateFrom;

    @NotNull
    private Duration duration;

    @NotBlank
    private String destination;

    private String description;

    private List<PriceDTO> prices = new ArrayList<>();
}
