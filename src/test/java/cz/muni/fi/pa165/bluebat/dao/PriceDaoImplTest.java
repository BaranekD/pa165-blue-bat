package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.PersistenceTravelAgencyApplicationContext;
import cz.muni.fi.pa165.bluebat.entity.Price;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Dominik Baranek <460705@mail.muni.cz/>
 */
@ContextConfiguration(classes = PersistenceTravelAgencyApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class PriceDaoImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PriceDao priceDao;

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Price price;
    private Price notInsertedPrice;

    @BeforeMethod
    public void setup(){
        setupPrice();
        setupNotInsertedPrice();
    }

    @AfterMethod
    public void cleanup() {
        removePrice(price);
    }

    @Test
    public void create_null_InvalidDataAccessApiUsageException() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> priceDao.create(null));
    }

    @Test
    public void create_valid() {
        Assertions.assertDoesNotThrow(() -> priceDao.create(notInsertedPrice));
        Assert.assertTrue(notInsertedPrice.getId() > 0L);

        Price found = priceDao.findById(notInsertedPrice.getId());

        Assert.assertNotNull(found);
        Assert.assertEquals(found, notInsertedPrice);
    }

    @Test
    public void update_null_InvalidDataAccessApiUsageException() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> priceDao.update(null));
    }

    @Test
    public void update_valid() {
        price.setAmount(BigDecimal.ZERO);
        Assertions.assertDoesNotThrow(() -> priceDao.update(price));
        Assertions.assertEquals(BigDecimal.ZERO, priceDao.findById(price.getId()).getAmount());
    }

    @Test
    public void delete_null_InvalidDataAccessApiUsageException() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> priceDao.delete(null));
    }

    @Test
    public void delete_notInserted() {
        Assertions.assertDoesNotThrow(() -> priceDao.delete(notInsertedPrice));
    }

    @Test
    public void findById_notInserted() {
        Price found = priceDao.findById(2024L);
        Assert.assertNull(found);
    }

    @Test
    public void findById_valid() {
        Price found = priceDao.findById(price.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(found, price);
    }

    @Test
    public void findById_removedPrice() {
        removePrice(price);

        Price found = priceDao.findById(price.getId());
        Assert.assertNull(found);
    }

    private void setupPrice() {
        price = new Price();
        price.setAmount(BigDecimal.ONE.setScale(2));
        price.setValidFrom(LocalDate.now());

        insertPrice(price);
    }

    private void setupNotInsertedPrice() {
        notInsertedPrice = new Price();
        notInsertedPrice.setAmount(BigDecimal.TEN);
        notInsertedPrice.setValidFrom(LocalDate.now());
    }

    private void insertPrice(Price price) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(price);
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }

    private void removePrice(Price price) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.contains(price) ? price : em.merge(price));
            em.getTransaction().commit();
        } finally {
            if (em != null) em.close();
        }
    }
}
