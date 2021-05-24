package cz.muni.fi.pa165.bluebat.security;

import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/rest/customer/isAdmin", "/rest/reservations/create"})
public class AuthenticationFilter extends ProtectFilter {

    public AuthenticationFilter(){
        super(false);
    }
}
