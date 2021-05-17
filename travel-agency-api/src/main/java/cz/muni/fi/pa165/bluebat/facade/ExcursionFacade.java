package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dto.ExcursionCreateDTO;
import cz.muni.fi.pa165.bluebat.dto.ExcursionDTO;

/**
 * A facade layer interface for manipulating with Excursion.
 *
 * Created by Tomáš Hampl on 27.4.21.
 */
public interface ExcursionFacade {

    /**
     * Create a new Excursion in system
     * @param excursionCreateDTO Excursion DTO to be created
     */
    ExcursionDTO createExcursion(ExcursionCreateDTO excursionCreateDTO);

    /**
     * Update an excursion in system
     * @param excursionDTO Excursion DTO to be updated
     */
    ExcursionDTO updateExcursion(ExcursionDTO excursionDTO);

    /**
     * Delete an excursion in system
     * @param id Long id of an excursion to be deleted
     */
    void deleteExcursion(Long id);

    /**
     *
     * @param id Long id of an excursion to be found
     * @return Excursion DTO if exists, null otherwise
     */
    ExcursionDTO getExcursionById(Long id);
}
