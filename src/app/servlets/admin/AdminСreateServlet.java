package app.servlets.admin;

import app.models.DAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 5:15 PM
 */
public class AdminСreateServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        //display form

        Map<String, String> params = getParameterMap(req);
        String tableName = params.get("table");

        DAO dao = Helpers.getDao(tableName);
        try {
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("columns", dao.getColumnNames());
            dataModel.put("values", params);
            dataModel.put("tablename", tableName);
            if(params.containsKey("error")) {
                dataModel.put("error", params.get("error"));
            }

            dataModel.put("action", "create");
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
            if(!dao.create(params)) {
                redirect(req, resp, params);
            } else {
                resp.sendRedirect("/admin/?table="+table);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            params.put("error", e.getMessage());
            redirect(req, resp, params);
        }

    }
}
