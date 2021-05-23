package cz.muni.fi.pa165.bluebat.security;

import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/trips/create"})
public class AuthenticationAndAdminFilter extends ProtectFilter{

    public AuthenticationAndAdminFilter(){
        super(true);
    }
}
