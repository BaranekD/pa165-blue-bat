package cz.muni.fi.pa165.bluebat.controllers;

import cz.muni.fi.pa165.bluebat.bean.AuthenticationBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AuthController {

    @GetMapping(path = "/login")
    public AuthenticationBean basicauth() {
        return new AuthenticationBean("You are authenticated");
    }
}
