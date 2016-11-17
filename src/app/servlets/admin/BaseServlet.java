package app.servlets.admin;

import app.QueryString;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 11:27 PM
 */
public class BaseServlet extends app.servlets.BaseServlet {

    protected static void redirect(HttpServletRequest req, HttpServletResponse resp, Map<String, String> params)
            throws IOException {
        resp.sendRedirect(req.getServletPath() + QueryString.encodeStrings(params));
    }


}
