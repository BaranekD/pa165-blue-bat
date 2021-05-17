package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;

/**
 * A service layer interface for the Excursion entity.
 *
 * Created by Tomáš Hampl on 3.3.21.
 */
public interface ExcursionService {

    /**
     * Create a new Excursion
     * @param excursion Excursion to be created
     */
    void create(Excursion excursion, Trip trip) throws WrongDataAccessException;

    /**
     * Update an excursion
     * @param excursion Excursion to be updated
     */
    void update(Excursion excursion) throws WrongDataAccessException;

    /**
     * Delete an excursion
     * @param excursion Excursion to be removed
     */
    void delete(Excursion excursion) throws WrongDataAccessException;

    /**
     * Find an excursion by id
     * @param id Long id of an excursion to be found
     * @return Excursion if exists, null otherwise
     */
    Excursion findById(Long id) throws WrongDataAccessException;
}
