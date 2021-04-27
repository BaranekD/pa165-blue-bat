package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.ServiceConfiguration;
import cz.muni.fi.pa165.bluebat.dao.PriceDao;
import cz.muni.fi.pa165.bluebat.entity.Price;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class PriceServiceImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private PriceDao priceDao;

    private PriceService priceService;

    @DataProvider(name = "priceValues")
    private static Object[][] getPriceValues() {
        return new Price[][] {
                { null },
                { getPrice(new BigDecimal(15), LocalDate.of(2022, 1, 1)) },
        };
    }

    @DataProvider(name = "createPriceLists")
    private static Object[][] getCreatePriceLists() {
        Price first = getPrice(new BigDecimal(15), LocalDate.of(2022, 1, 1));
        Price second = getPrice(new BigDecimal(7), LocalDate.of(2021, 3, 11));
        Price third = getPrice(new BigDecimal(33), LocalDate.of(2022, 11, 25));
        return new Object[][] {
                { Collections.singletonList(first) },
                { Arrays.asList(first, second) },
                { Arrays.asList(first, second, third) },
        };
    }

    @DataProvider(name = "deletePriceLists")
    private static Object[][] getDeletePriceLists() {
        Price first = getPrice(new BigDecimal(15), LocalDate.of(2022, 1, 1));
        first.setId(1L);
        Price second = getPrice(new BigDecimal(7), LocalDate.of(2021, 3, 11));
        second.setId(2L);
        Price third = getPrice(new BigDecimal(33), LocalDate.of(2022, 11, 25));
        third.setId(3L);
        return new Object[][] {
                { Collections.singletonList(first) },
                { Arrays.asList(first, second) },
                { Arrays.asList(first, second, third) },
        };
    }

    @DataProvider(name = "updatePriceLists")
    private static Object[][] getUpdatePriceLists() {
        Price oldFirst = getPrice(new BigDecimal(15), LocalDate.of(2022, 1, 1));
        oldFirst.setId(1L);
        Price oldSecond = getPrice(new BigDecimal(7), LocalDate.of(2021, 3, 11));
        oldSecond.setId(2L);
        Price oldThird = getPrice(new BigDecimal(33), LocalDate.of(2022, 11, 25));
        oldThird.setId(3L);
        Price newFirst = getPrice(new BigDecimal(16), LocalDate.of(2021, 2, 1));
        newFirst.setId(1L);
        Price newSecond = getPrice(new BigDecimal(8), LocalDate.of(2021, 3, 5));
        newSecond.setId(2L);
        Price newThird = getPrice(new BigDecimal(34), LocalDate.of(2023, 11, 18));
        newThird.setId(3L);
        return new Object[][] {
                { Collections.singletonList(oldFirst), Collections.singletonList(newFirst) },
                { Arrays.asList(oldFirst, oldSecond), Arrays.asList(newFirst, newSecond) },
                { Arrays.asList(oldFirst, oldSecond, oldThird), Arrays.asList(newFirst, newSecond, newThird) },
        };
    }

    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.openMocks(this);
        priceService = new PriceServiceImpl(priceDao);

        doNothing().when(priceDao).create(any());
        doNothing().when(priceDao).update(any());
        doNothing().when(priceDao).delete(any());
    }

    @Test(dataProvider = "priceValues")
    public void create_called(Price price) {
        priceService.create(price);
        verify(priceDao, times(1)).create(price);
    }

    @Test(dataProvider = "priceValues")
    public void update_called(Price price) {
        priceService.update(price);
        verify(priceDao, times(1)).update(price);
    }

    @Test
    public void updatePrices_emptyLists() {
        priceService.updatePrices(new LinkedList<>(), new LinkedList<>());

        verify(priceDao, never()).create(any());
        verify(priceDao, never()).update(any());
        verify(priceDao, never()).delete(any());
    }

    @Test(dataProvider = "createPriceLists")
    public void updatePrices_onlyCreating(List<Price> prices) {
        priceService.updatePrices(new LinkedList<>(), prices);

        for (Price price: prices) verify(priceDao, times(1)).create(price);
        verify(priceDao, never()).update(any());
        verify(priceDao, never()).delete(any());
    }

    @Test(dataProvider = "deletePriceLists")
    public void updatePrices_onlyDeleting(List<Price> prices) {
        priceService.updatePrices(prices, new LinkedList<>());

        verify(priceDao, never()).create(any());
        verify(priceDao, never()).update(any());
        for (Price price: prices) verify(priceDao, times(1)).delete(price);
    }

    @Test(dataProvider = "updatePriceLists")
    public void updatePrices_onlyUpdating(List<Price> oldPrices, List<Price> newPrices) {
        priceService.updatePrices(oldPrices, newPrices);

        verify(priceDao, never()).create(any());
        for (Price price: newPrices) verify(priceDao, times(1)).update(price);
        verify(priceDao, never()).delete(any());
    }

    @Test
    public void updatePrices_oneCreatedUpdatedDeleted() {
        Price oldDeleted = getPrice(new BigDecimal(7), LocalDate.of(2022, 8, 8));
        oldDeleted.setId(1L);
        Price newCreated = getPrice(new BigDecimal(15), LocalDate.of(2022, 3, 1));
        Price oldUpdated = getPrice(new BigDecimal(22), LocalDate.of(2021, 1, 10));
        Price newUpdated = getPrice(new BigDecimal(12), LocalDate.of(2020, 2, 1));
        oldUpdated.setId(15L);
        newUpdated.setId(15L);

        priceService.updatePrices(Arrays.asList(oldDeleted, oldUpdated), Arrays.asList(newUpdated, newCreated));

        verify(priceDao, times(1)).create(newCreated);
        verify(priceDao, times(1)).update(newUpdated);
        verify(priceDao, times(1)).delete(oldDeleted);
    }

    @Test(dataProvider = "priceValues")
    public void delete_called(Price price) {
        priceService.delete(price);
        verify(priceDao, times(1)).delete(price);
    }

    private static Price getPrice(BigDecimal amount, LocalDate date) {
        Price result = new Price();
        result.setAmount(amount);
        result.setValidFrom(date);
        return result;
    }
}
