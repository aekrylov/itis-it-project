package app.filters;

import app.Helpers;
import app.models.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 12:09 PM
 */
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        try {
            User user = Helpers.getCurrentUser(request);
            if(user.getRole().equals("admin")) {
                chain.doFilter(req, resp);
            } else {
                response.sendRedirect("/login");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/login");
            return;
        }

    }

    @Override
    public void destroy() {

    }
}
