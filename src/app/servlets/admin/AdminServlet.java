package app.servlets.admin;

import app.models.DAO;
import app.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/17/16 12:19 PM
 */
public class AdminServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        //get table and display it
        //todo ordering and pagination

        String tablename = req.getParameter("table");
        if(tablename == null || tablename.equals("")) {
            //display main page
            Map<String, String> tables = Helpers.getTableTitles();
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("tables", tables);
            app.Helpers.render(getServletContext(), req, resp, "admin/main.ftl", dataModel);
            return;
        }

        DAO dao = Helpers.getDao(tablename);
        try {
            List<Object[]> list = dao.getTable();
            Map<String, Object> dataModel = new HashMap<>();

            dataModel.put("tablename", tablename);
            dataModel.put("rows", list);
            dataModel.put("columns", dao.getColumnNames());

            app.Helpers.render(getServletContext(), req, resp, "admin/table.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
