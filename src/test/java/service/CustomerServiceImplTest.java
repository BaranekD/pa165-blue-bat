package service;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Price;
import cz.muni.fi.pa165.bluebat.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;

@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
public class CustomerServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PriceService priceService;

    @Test
    public void createFindDeleteTest() {
        Price price = new Price();
        price.setAmount(new BigDecimal(15));

        priceService.create(price);
        Assert.assertNotNull(price);
    }

}
