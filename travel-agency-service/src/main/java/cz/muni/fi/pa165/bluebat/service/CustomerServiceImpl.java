package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.CustomerDao;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author : Rudolf Madoran
 * @since : 7. 4. 2021, Wed
 **/
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
        if (customer == null) {
            throw new IllegalArgumentException("Customer can not be null");
        }

        Customer previous = customerDao.findById(customer.getId());

        if (previous == null) {
            throw new IllegalArgumentException("Customer has not been found in database");
        }

        try {
            customerDao.delete(customer);
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while deleting a customer", ex);
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer can not be null");
        }

        try {
            customerDao.create(customer);
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while creating a customer", ex);
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer can not be null");
        }

        Customer previous = customerDao.findById(customer.getId());

        if (previous == null) {
            throw new IllegalArgumentException("Customer has not been found in database");
        }

        try {
            customerDao.update(customer);
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while updating a customer", ex);
        }
    }

    @Override
    public Customer findCustomerById(Long id) {
        try {
            return customerDao.findById(id);
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while finding a customer", ex);
        }
    }

    @Override
    public List<Customer> findAllCustomers() {
        try {
            return customerDao.findAll();
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while finding customers", ex);
        }
    }
}
