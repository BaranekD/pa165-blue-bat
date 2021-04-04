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
    void updateName(Customer customer, String name);
    void updateBirthday(Customer customer, LocalDate birthday);
    void updateNumber(Customer customer, String number);
    Customer findById(Long id);
    Customer findByNumber(String number);


}
