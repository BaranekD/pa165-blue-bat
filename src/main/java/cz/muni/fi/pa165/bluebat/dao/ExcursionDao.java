package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.entity.Excursion;

/**
 * A dao layer interface for manipulating with Excursion entity in database.
 *
 * Created by Tomáš Hampl on 3.3.21.
 */
public interface ExcursionDao {

    /**
     * Create a new Excursion in database
     * @param excursion Excursion to be created
     */
    void create(Excursion excursion);

    /**
     * Update an excursion in database
     * @param excursion Excursion to be updated
     */
    void update(Excursion excursion);

    /**
     * Delete an excursion in database
     * @param excursion Excursion to be removed
     */
    void delete(Excursion excursion);

    /**
     * Find an excursion in database by id
     * @param id Long id of an excursion to be found
     * @return Excursion if exists, null otherwise
     */
    Excursion findById(Long id);
}
