package cz.muni.fi.pa165.bluebat.controllers;

import cz.muni.fi.pa165.bluebat.dto.CustomerAuthenticateDTO;
import cz.muni.fi.pa165.bluebat.dto.CustomerDTO;
import cz.muni.fi.pa165.bluebat.facade.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController{

    @Autowired
    private CustomerFacade customerFacade;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public boolean login(@RequestBody CustomerAuthenticateDTO customerAuthenticateDTO) {
        return customerFacade.authenticate(customerAuthenticateDTO);
    }

    @RequestMapping(value = "/isAdmin",method = RequestMethod.GET)
    public boolean isAdmin(@RequestAttribute("authenticatedUser") CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return false;
        }
        return customerDTO.getIsAdmin();
    }

}
