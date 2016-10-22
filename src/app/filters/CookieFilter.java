package app.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 6:50 PM
 * 11-501
 * Filter that looks for user cookie and restores session params if cookie found
 */
@WebFilter(filterName = "CookieFilter")
public class CookieFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest)req;
        Cookie[] cookies = httpRequest.getCookies();

        if(cookies != null) {
            for (Cookie cookie : cookies) {
                //todo security
                if (cookie.getName().equals("username")) {
                    //found logged in cookie, set session variable
                    httpRequest.getSession().setAttribute("username", cookie.getValue());
                    break;
                }
            }
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
