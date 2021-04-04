package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.entity.Excursion;

/**
 * Created by Tomáš Hampl on 3.3.21.
 */

public interface ExcursionService {

    void create(Excursion excursion);

    void update(Excursion excursion);

    void delete(Excursion excursion);

    Excursion findById(Long id);
}
