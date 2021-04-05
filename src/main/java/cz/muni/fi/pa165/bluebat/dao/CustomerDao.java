package cz.muni.fi.pa165.bluebat.dao;

import cz.muni.fi.pa165.bluebat.entity.Customer;

import java.time.LocalDate;
import java.util.List;

/**
 * @author rudolf
 */
public interface CustomerDao {

    void create(Customer customer);
    void remove(Customer customer);
    void update(Customer customer);
    Customer findById(Long id);
    Customer findByPhoneNumber(Long number);
    Customer findByName(String name,String surname);


}
