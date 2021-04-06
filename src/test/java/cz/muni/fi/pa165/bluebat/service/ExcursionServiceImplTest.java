package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertNull;

/**
 * @author Dominik Baranek <460705@mail.muni.cz>
 */
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class ExcursionServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private ExcursionService excursionService;

    @Test
    public void createFindUpdateDeleteTest(){
        Excursion e = new Excursion();
        e.setName("testName");
        e.setDuration(Duration.ZERO);
        e.setDateFrom(LocalDate.now());
        e.setDestination("testDestination");
        e.setDescription("testDescription");

        excursionService.create(e);
        assertEquals(excursionService.findById(e.getId()).getName(),"testName");

        e.setName("testNameUpdated");
        excursionService.update(e);
        assertEquals(excursionService.findById(e.getId()).getName(),"testNameUpdated");

        excursionService.delete(e);
        assertNull(excursionService.findById(e.getId()));
    }

    @Test
    public void createExcursionNullArgumentThrowsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> excursionService.create(null));
    }

    @Test
    public void updateExcursionNullArgumentThrowsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> excursionService.update(null));
    }

    @Test
    public void deleteExcursionNullArgumentThrowsInvalidDataAccessApiUsageException() {
        assertThrows(InvalidDataAccessApiUsageException.class, () -> excursionService.delete(null));
    }

    @Test
    public void findExcursionNonExistingIdReturnsNull() {
        Excursion e = excursionService.findById(2048L);
        assertNull(e);
    }
}
