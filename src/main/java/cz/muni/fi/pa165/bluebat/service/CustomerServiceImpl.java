package cz.muni.fi.pa165.bluebat.service;/* created by rudolf */

import cz.muni.fi.pa165.bluebat.dao.CustomerDao;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

/**
 * @author rudolf
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void removeCustomer(Customer customer) {
        customerDao.remove(customer);
    }

    @Override
    public void addCustomer(Customer customer) {
        customerDao.create(customer);
    }

    @Override
    public void updateCustomerName(Customer customer) {
        customerDao.update(customer);
    }


    @Override
    public Customer findCustomerById(Long id) {
        return customerDao.findById(id);
    }

    @Override
    public Customer findCustomerByName(String name, String surname) {
        return customerDao.findByName(name,surname);
    }

    @Override
    public Customer findCustomerByPhoneNumber(Long number) {
        return customerDao.findByPhoneNumber(number);
    }
}