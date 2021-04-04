package cz.muni.fi.pa165.bluebat.service;/* created by rudolf */

import cz.muni.fi.pa165.bluebat.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author rudolf
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerDao customerDao;

}
