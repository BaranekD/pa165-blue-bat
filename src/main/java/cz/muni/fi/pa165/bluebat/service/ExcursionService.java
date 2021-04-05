package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.entity.Excursion;

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
    void create(Excursion excursion);

    /**
     * Update an excursion
     * @param excursion Excursion to be updated
     */
    void update(Excursion excursion);

    /**
     * Delete an excursion
     * @param excursion Excursion to be removed
     */
    void delete(Excursion excursion);

    /**
     * Find an excursion by id
     * @param id Long id of an excursion to be found
     * @return Excursion if exists, null otherwise
     */
    Excursion findById(Long id);
}
