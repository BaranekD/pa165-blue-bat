package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.entity.Price;

/**
 * Created by Tomáš Hampl on 3.3.21.
 */

public interface PriceService {

    void create(Price price);

    void update(Price price);

    void delete(Price price);

    Price findById(Long id);
}
