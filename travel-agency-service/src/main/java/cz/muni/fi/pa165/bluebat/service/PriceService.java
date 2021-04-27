package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.entity.Price;

import java.util.List;

/**
 *  A service layer interface for the Price entity.
 *
 * Created by Tomáš Hampl on 3.3.21.
 */
public interface PriceService {

    /**
     * Create a new Price
     * @param price Price to be created
     */
    void create(Price price);

    /**
     * Update a price
     * @param price Price to be updated
     */
    void update(Price price);

    /**
     * Update a list of prices
     * @param oldPrices an old List of prices that is not up to date
     * @param newPrices a new list od prices that contains current values
     */
    void updatePrices(List<Price> oldPrices,List<Price> newPrices);

    /**
     * Delete a price
     * @param price Price to be removed
     */
    void delete(Price price);
}
