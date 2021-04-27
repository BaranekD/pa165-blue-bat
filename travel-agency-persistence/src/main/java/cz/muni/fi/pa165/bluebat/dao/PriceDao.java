package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.entity.Price;

/**
 *  A dao layer interface for manipulating with Price entity in database.
 *
 * Created by Tomáš Hampl on 3.3.21.
 */
public interface PriceDao {

    /**
     * Create a new Price in database
     * @param price Price to be created
     */
    void create(Price price);

    /**
     * Update a price in database
     * @param price Price to be updated
     */
    void update(Price price);

    /**
     * Delete a price in database
     * @param price Price to be removed
     */
    void delete(Price price);

    /**
     * Find a price in database by id
     * @param id Long id of a price to be found
     * @return Price if exists, null otherwise
     */
    Price findById(Long id);
}
