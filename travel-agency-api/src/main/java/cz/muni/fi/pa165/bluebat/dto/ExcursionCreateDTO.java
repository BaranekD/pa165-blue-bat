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
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExcursionCreateDTO)) return false;

        ExcursionCreateDTO excursion = (ExcursionCreateDTO) o;

        if (getParentId() != null ? !getParentId().equals(excursion.getParentId()) : excursion.getParentId() != null)
            return false;
        if (getName() != null ? !getName().equals(excursion.getName()) : excursion.getName() != null)
            return false;
        if (getDateFrom() != null ? !getDateFrom().equals(excursion.getDateFrom()) : excursion.getDateFrom() != null)
            return false;
        if (getDuration() != null ? !getDuration().equals(excursion.getDuration()) : excursion.getDuration() != null)
            return false;
        if (getDestination() != null ? !getDestination().equals(excursion.getDestination()) : excursion.getDestination() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(excursion.getDescription()) : excursion.getDescription() != null)
            return false;
        return getPrices() != null ? getPrices().equals(excursion.getPrices()) : excursion.getPrices() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParentId(), getName(), getDateFrom(), getDuration(), getDestination(), getDescription(), getPrices());
    }
}
