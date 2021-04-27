package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.entity.Price;

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
     * Delete a price
     * @param price Price to be removed
     */
    void delete(Price price);

    /**
     * Find a price by id
     * @param id Long id of a price to be found
     * @return Price if exists, null otherwise
     */
    Price findById(Long id);
}
