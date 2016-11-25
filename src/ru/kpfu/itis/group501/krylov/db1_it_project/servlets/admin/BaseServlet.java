package ru.kpfu.itis.group501.krylov.db1_it_project.servlets.admin;

import ru.kpfu.itis.group501.krylov.db1_it_project.misc.QueryString;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 11:27 PM
 */
public class BaseServlet extends ru.kpfu.itis.group501.krylov.db1_it_project.servlets.BaseServlet {

    protected void addPathInfo(Map<String, String> map, HttpServletRequest req) {
    }


    protected static void redirect(HttpServletRequest req, HttpServletResponse resp, Map<String, String> params)
            throws IOException {
        resp.sendRedirect(req.getServletPath() + QueryString.encodeStrings(params));
    }


}
