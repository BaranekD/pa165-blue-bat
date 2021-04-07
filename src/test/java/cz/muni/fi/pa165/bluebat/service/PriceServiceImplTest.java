package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertNull;

/**
 * @author Dominik Baranek <460705@mail.muni.cz>
 */
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class PriceServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PriceService priceService;

    @Test
    public void createFindUpdateDeleteTest(){
        Price p = new Price();
        p.setAmount(BigDecimal.ONE);
        p.setValidFrom(LocalDate.now());

        priceService.create(p);
        assertEquals(priceService.findById(p.getId()).getAmount(), BigDecimal.ONE.setScale(2));

        p.setAmount(BigDecimal.TEN);
        priceService.update(p);
        assertEquals(priceService.findById(p.getId()).getAmount(), BigDecimal.TEN.setScale(2));

        priceService.delete(p);
        assertNull(priceService.findById(p.getId()));
    }

    @Test
    public void createPriceNullArgumentThrowsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> priceService.create(null));
    }

    @Test
    public void updatePriceNullArgumentThrowsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> priceService.update(null));
    }

    @Test
    public void deletePriceNullArgumentThrowsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> priceService.delete(null));
    }

    @Test
    public void findPriceNonExistingIdReturnsNull() {
        Price p = priceService.findById(2048L);
        assertNull(p);
    }
}
