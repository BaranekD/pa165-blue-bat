package cz.muni.fi.pa165.bluebat.service;/* created by rudolf */

import cz.muni.fi.pa165.bluebat.dao.CustomerDao;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


/**
 * @author rudolf
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{


    private final CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerDao.delete(customer);
    }

    @Override
    public void addCustomer(Customer customer) {

        customerDao.create(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {

        customerDao.update(customer);
    }

    @Override
    public Customer findCustomerById(Long id) {

        return customerDao.findById(id);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerDao.findAll();
    }
}
