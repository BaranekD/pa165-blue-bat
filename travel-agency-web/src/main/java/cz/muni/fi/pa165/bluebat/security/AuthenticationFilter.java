package cz.muni.fi.pa165.bluebat.security;

import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/customer/isAdmin","/reservations/create"})
public class AuthenticationFilter extends ProtectFilter{

    public AuthenticationFilter(){
        super(false);
    }
}
