package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;

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
     *              If it is null then throws IllegalArgumentException.
     */
    void create(Price price) throws WrongDataAccessException;

    /**
     * Update a price
     * @param price Price to be updated.
     *              If it is null or is not in the database then throws IllegalArgumentException.
     */
    void update(Price price) throws WrongDataAccessException;

    /**
     * Update a list of prices
     * @param oldPrices an old List of prices that is not up to date.
     *                  If it is null then throws IllegalArgumentException.
     * @param newPrices a new list od prices that contains current values.
     *                  If it is null then throws IllegalArgumentException.
     */
    void updatePrices(List<Price> oldPrices,List<Price> newPrices) throws WrongDataAccessException;

    /**
     * Delete a price
     * @param price Price to be removed
     */
    void delete(Price price) throws WrongDataAccessException;
}
