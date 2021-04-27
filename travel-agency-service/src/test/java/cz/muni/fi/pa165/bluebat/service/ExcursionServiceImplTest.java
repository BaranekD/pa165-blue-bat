package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dao.ExcursionDao;
import cz.muni.fi.pa165.bluebat.entity.Excursion;
import cz.muni.fi.pa165.bluebat.entity.Price;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class ExcursionServiceImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private ExcursionDao excursionDao;

    @Mock
    private PriceService priceService;

    private ExcursionService excursionService;

    @DataProvider(name = "excursions")
    private static Object[][] getPriceValues() {
        return new Excursion[][] {
                { null },
                { getDefaultInsertedExcursion() },
        };
    }

    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
        excursionService = new ExcursionServiceImpl(excursionDao, priceService);

        doNothing().when(excursionDao).create(any());
        doNothing().when(excursionDao).update(any());
        doNothing().when(excursionDao).delete(any());
    }

    @Test
    public void create_valid() {
        Excursion excursion = getDefaultExcursion();
        excursionService.create(excursion);
        verify(excursionDao, times(1)).create(excursion);
    }

    @Test
    public void create_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> excursionService.create(null));
    }

    @Test
    public void update_called() {
        Excursion excursion = getDefaultInsertedExcursion();
        Long id = excursion.getId();
        when(excursionService.findById(id)).thenReturn(excursion);

        excursionService.update(excursion);

        verify(priceService, times(1)).updatePrices(excursion.getPrices(), excursion.getPrices());
        verify(excursionDao, times(1)).update(excursion);
    }

    @Test
    public void update_changedPrices() {
        Excursion oldExcursion = getDefaultInsertedExcursion();
        oldExcursion.setPrices(Arrays.asList(
                getPrice(1L, new BigDecimal(15L), LocalDate.of(2021, 1, 22)),
                getPrice(2L, new BigDecimal(12L), LocalDate.of(2021, 8, 3)),
                getPrice(3L, new BigDecimal(30L), LocalDate.of(2022, 11, 6))
        ));
        Excursion newExcursion = getDefaultInsertedExcursion();
        newExcursion.setPrices(Arrays.asList(
                getPrice(1L, new BigDecimal(15L), LocalDate.of(2021, 1, 22)),
                getPrice(3L, new BigDecimal(30L), LocalDate.of(2022, 11, 6)),
                getPrice(4L, new BigDecimal(12L), LocalDate.of(2021, 8, 3))
        ));
        newExcursion.setName("New test excursion");

        Long id = oldExcursion.getId();
        when(excursionService.findById(id)).thenReturn(oldExcursion);

        excursionService.update(newExcursion);

        verify(priceService, times(1)).updatePrices(oldExcursion.getPrices(), newExcursion.getPrices());
        verify(excursionDao, times(1)).update(newExcursion);
    }

    @Test
    public void update_null_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> excursionService.update(null));
    }

    @Test
    public void update_notInserted_IllegalArgumentException() {
        Excursion excursion = getDefaultInsertedExcursion();
        Long id = excursion.getId();
        when(excursionService.findById(id)).thenReturn(null);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> excursionService.update(excursion));
    }

    @Test(dataProvider = "excursions")
    public void delete_called(Excursion excursion) {
        Assertions.assertDoesNotThrow(() -> excursionService.delete(excursion));
        verify(excursionDao, times(1)).delete(excursion);
    }

    private static Excursion getDefaultExcursion() {
        Excursion result = new Excursion();
        result.setName("Test excursion");
        result.setDateFrom(LocalDate.of(2022, 4, 25));
        result.setDuration(Duration.ofHours(4));
        result.setDestination("Madrid");
        result.setDescription("climbing");

        return result;
    }

    private static Excursion getDefaultInsertedExcursion() {
        Excursion result = getDefaultExcursion();
        result.setId(1L);
        return result;
    }

    private static Price getPrice(Long id, BigDecimal amount, LocalDate date) {
        Price result = new Price();
        result.setId(id);
        result.setAmount(amount);
        result.setValidFrom(date);
        return result;
    }
}
