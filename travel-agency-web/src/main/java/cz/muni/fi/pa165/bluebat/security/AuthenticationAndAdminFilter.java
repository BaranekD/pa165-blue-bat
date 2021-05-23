package cz.muni.fi.pa165.bluebat.security;

public class AuthenticationAndAdminFilter extends ProtectFilter{

    public AuthenticationAndAdminFilter(){
        super(true);
    }
}
