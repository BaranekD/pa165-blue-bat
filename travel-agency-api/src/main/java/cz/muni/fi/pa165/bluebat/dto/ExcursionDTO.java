package cz.muni.fi.pa165.bluebat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Tomáš Hampl on 27.4.21.
 */
@Getter
@Setter
public class ExcursionDTO {
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

    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExcursionDTO)) return false;

        ExcursionDTO excursion = (ExcursionDTO) o;

        if (getId() != null ? !getId().equals(excursion.getId()) : excursion.getId() != null)
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
        return getPrice() != null ? getPrice().equals(excursion.getPrice()) : excursion.getPrice() == null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDateFrom(), getDuration(), getDestination(), getDescription(), getPrice());
    }
}
