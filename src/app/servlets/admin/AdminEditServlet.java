package app.servlets.admin;

import app.models.DAO;
import app.models.Entity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 8:44 PM
 */
public class AdminEditServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        //display form

        Map<String, String> params = getParameterMap(req);
        String tableName = params.get("table");
        int id = Integer.parseInt(params.get("id"));

        DAO<?> dao = Helpers.getDao(tableName);
        Map<String, Object> dataModel = new HashMap<>();

        try {
            if(!params.containsKey("edited")) {
                Map<String, String> entityMap = dao.getMap(id);
                dataModel.put("values", entityMap);
            } else {
                dataModel.put("values", params);
            }

            dataModel.put("columns", dao.getColumnNames());
            dataModel.put("tablename", tableName);
            if(params.containsKey("error")) {
                dataModel.put("error", params.get("error"));
            }

            app.Helpers.render(getServletContext(), req, resp, "admin/create.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        Map<String, String> params = getParameterMap(req);
        String table = params.get("table");

        DAO dao = Helpers.getDao(table);

        try {
            if(dao.update(params)) {
                resp.sendRedirect("/admin/?table="+table);
                return;
            } else {
                params.put("error", "0 rows updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            params.put("error", e.getMessage());
        }

        params.put("edited", "true");
        redirect(req, resp, params);
    }
}
