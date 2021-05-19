package cz.muni.fi.pa165.bluebat.service;

import cz.muni.fi.pa165.bluebat.dao.CustomerDao;
import cz.muni.fi.pa165.bluebat.entity.Customer;
import cz.muni.fi.pa165.bluebat.exceptions.WrongDataAccessException;
import cz.muni.fi.pa165.bluebat.utils.Validator;
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
        Validator.NotNull(customer,"Customer");

        try {
            customerDao.delete(customer);
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while deleting a customer", ex);
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        Validator.NotNull(customer,"Customer");
        try {
            customerDao.create(customer);
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while creating a customer", ex);
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        Validator.NotNull(customer,"Customer");

        Customer previous;

        try {
            previous = customerDao.findById(customer.getId());
        } catch (Exception e) {
            throw new WrongDataAccessException("Customer dao layer exception", e);
        }

        Validator.Found(previous, "Customer");

        try {
            customerDao.update(customer);
        } catch (Exception ex) {
            throw new WrongDataAccessException("Error while updating a customer", ex);
        }
    }

    @Override
    public Customer findCustomerById(Long id) {
        Validator.Positive(id, "Customer id");

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
