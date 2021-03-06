package cz.muni.fi.pa165.bluebat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Tomáš Hampl on 3.3.21.
 */
@Entity
@Getter
@Setter
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private LocalDate validFrom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;

        Price price = (Price) o;

        if (getAmount() != null ? !getAmount().equals(price.getAmount()) : price.getAmount() != null)
            return false;
        return getValidFrom() != null ? getValidFrom().equals(price.getValidFrom()) : price.getValidFrom() == null;
    }

    @Override
    public int hashCode() {
        int result = getAmount() != null ? getAmount().hashCode() : 0;
        result = 37 * result - + (getValidFrom() != null ? getValidFrom().hashCode() : 0);
        return result;
    }
}
