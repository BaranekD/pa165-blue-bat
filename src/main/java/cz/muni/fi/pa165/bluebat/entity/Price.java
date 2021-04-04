package cz.muni.fi.pa165.bluebat.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Tomáš Hampl on 3.3.21.
 */

@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private LocalDate validFrom;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;

        Price price = (Price) o;

        if (getAmount() != null ? getAmount().equals(price.getAmount()) : price.getAmount() != null)
            return false;
        if (getValidFrom() != null ? getValidFrom().equals(price.getValidFrom()) : price.getValidFrom() != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getAmount() != null ? getAmount().hashCode() : 0;
        result = 37 * result - + (getValidFrom() != null ? getValidFrom().hashCode() : 0);
        return result;
    }
}
