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
    private CustomerDao customerDao;

    @Override
    public void removeCustomer(Customer customer) {
        customerDao.remove(customer);
    }

    @Override
    public void addCustomer(Customer customer) {
        customerDao.create(customer);
    }

    @Override
    public void updateCustomerName(Customer customer, String name) {
        customerDao.updateName(customer,name);
    }

    @Override
    public void updateCustomerNumber(Customer customer, String name) {
        customerDao.updateNumber(customer,name);
    }

    @Override
    public void updateCustomerBirthday(Customer customer, LocalDate birthday) {
        customerDao.updateBirthday(customer,birthday);
    }

    @Override
    public Customer findCustomerById(Long id) {
        return customerDao.findById(id);
    }

    @Override
    public Customer findCustomerByNumber(String number) {
        return customerDao.findByNumber(number);
    }
}