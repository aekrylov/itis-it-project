package ru.kpfu.itis.group501.krylov.db1_it_project.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 6:49 PM
 * 11-501
 * If user is not authorized, redirect to login page
 */
public class AuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        if(request.getSession().getAttribute("username") != null) {
            chain.doFilter(req, resp);
        } else {
            response.sendRedirect("/login");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
