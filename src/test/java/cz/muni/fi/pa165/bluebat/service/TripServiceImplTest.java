package cz.muni.fi.pa165.bluebat.service;


import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.LocalDate;
import java.util.HashSet;


/**
 * Test for service of Trip entity.
 *
 * @author : Rudolf Madoran
 * @since : 7. 4. 2021, Wed
 **/
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class TripServiceImplTest extends AbstractTestNGSpringContextTests {
    /**
     * TODO
     */
}
