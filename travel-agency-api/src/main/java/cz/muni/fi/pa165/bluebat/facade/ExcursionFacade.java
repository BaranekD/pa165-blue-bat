package cz.muni.fi.pa165.bluebat.facade;

import cz.muni.fi.pa165.bluebat.dto.ExcursionDTO;

/**
 * A facade layer interface for manipulating with Excursion.
 *
 * Created by Tomáš Hampl on 27.4.21.
 */
public interface ExcursionFacade {

    void createExcursion(ExcursionDTO excursionDTO);

    void updateExcursion(ExcursionDTO excursionDTO);

    void deleteExcursion(Long id);

    ExcursionDTO getExcursionById(Long id);
}
