package cz.muni.fi.pa165.bluebat.security;

import cz.muni.fi.pa165.bluebat.dto.CustomerAuthenticateDTO;
import cz.muni.fi.pa165.bluebat.dto.CustomerDTO;
import cz.muni.fi.pa165.bluebat.facade.CustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;


public abstract class ProtectFilter implements Filter {

    @Autowired
    private CustomerFacade customerFacade;

    private boolean onlyAdmins;

    public ProtectFilter(boolean onlyAdmins){
        this.onlyAdmins=onlyAdmins;
    }

    @Override
    public void doFilter(ServletRequest r, ServletResponse s, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;

        String auth = request.getHeader("Authorization");
        if (auth == null) {
            response401(response);
            return;
        }

        String[] creds = parseAuthHeader(auth);
        String nickName = creds[0];
        String password = creds[1];

        CustomerDTO matchingCustomer = customerFacade.getCustomerByNickName(nickName);
        if (matchingCustomer == null) {
            response401(response);
            return;
        }

        CustomerAuthenticateDTO customerAuthenticateDTO = new CustomerAuthenticateDTO();
        customerAuthenticateDTO.setNickName(matchingCustomer.getNickName());
        customerAuthenticateDTO.setPassword(password);

        if (!customerFacade.authenticate(customerAuthenticateDTO)) {
            response401(response);
            return;
        }

        if (!customerFacade.isAdmin(matchingCustomer) && onlyAdmins) {
            response401(response);
            return;
        }

        request.setAttribute("authenticatedUser", matchingCustomer);
        chain.doFilter(request, response);
    }


    private String[] parseAuthHeader(String auth) {
        return new String(Base64.getDecoder().decode(auth.split(" ")[1])).split(":", 2);
    }

    private void response401(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=\"type nickname and password\"");
        response.getWriter().println("<html><body><h1>401 Unauthorized</h1> Go away ...</body></html>");
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}
