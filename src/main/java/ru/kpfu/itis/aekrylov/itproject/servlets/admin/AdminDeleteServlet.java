package ru.kpfu.itis.aekrylov.itproject.servlets.admin;

import ru.kpfu.itis.aekrylov.itproject.models.DAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 11:41 PM
 */
public class AdminDeleteServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        Map<String, String> params = getParameterMap(req);
        String table = params.get("table");
        int id = Integer.parseInt(params.get("id"));

        DAO dao = Helpers.getDao(table);
        try {
            dao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("/admin/?table="+table);
    }
}
