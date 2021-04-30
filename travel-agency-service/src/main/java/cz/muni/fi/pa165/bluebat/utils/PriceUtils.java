package cz.muni.fi.pa165.bluebat.utils;

import cz.muni.fi.pa165.bluebat.entity.Price;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PriceUtils {

    public static BigDecimal getPriceAmountByDate(LocalDate date, List<Price> prices) {
        Price result = prices.get(0);

        for (Price price : prices) {
            if (price.getValidFrom().isAfter(result.getValidFrom()) && price.getValidFrom().isBefore(date)) {
                result = price;
            }
        }

        return result.getAmount();
    }

    public static BigDecimal getCurrentPrice(List<Price> prices) {
        return getPriceAmountByDate(LocalDate.now(), prices);
    }
}
