package cz.muni.fi.pa165.bluebat.service;


import cz.muni.fi.pa165.bluebat.entity.Customer;

import java.time.LocalDate;

/**
 * @author rudolf
 */
public interface CustomerService {

    void removeCustomer(Customer customer);
    void addCustomer(Customer customer);
    void updateCustomerName(Customer customer);
    Customer findCustomerById(Long id);
    Customer findCustomerByName(String name,String surname);
    Customer findCustomerByPhoneNumber(Long number);




}