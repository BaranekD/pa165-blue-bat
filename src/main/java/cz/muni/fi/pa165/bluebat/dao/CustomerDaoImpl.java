package cz.muni.fi.pa165.bluebat.dao;/* created by rudolf */

import cz.muni.fi.pa165.bluebat.entity.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;


/**
 * @author rudolf
 */
@Repository
public class CustomerDaoImpl implements CustomerDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Customer customer) {
        em.persist(customer);
    }

    @Override
    public void remove(Customer customer) {
        em.remove(customer);

    }

    @Override
    public void updateName(Customer customer, String name) {
        em.find(Customer.class,customer.getId()).setName(name);
    }

    @Override
    public void updateBirthday(Customer customer, LocalDate birthday) {
        em.find(Customer.class,customer.getId()).setBirthday(birthday);
    }

    @Override
    public void updateNumber(Customer customer, String number) {
        em.find(Customer.class,customer.getId()).setNumber(number);
    }

    @Override
    public Customer findById(Long id) {
        return em.find(Customer.class,id);
    }

    @Override
    public Customer findByNumber(String number) {
        return em.createQuery("select e from Customer e where e.number = :number", Customer.class).setParameter(number,number).getSingleResult();
    }



}
