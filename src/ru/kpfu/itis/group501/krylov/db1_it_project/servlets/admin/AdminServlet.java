package ru.kpfu.itis.group501.krylov.db1_it_project.servlets.admin;

import ru.kpfu.itis.group501.krylov.db1_it_project.misc.CommonHelpers;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.ParameterMap;
import ru.kpfu.itis.group501.krylov.db1_it_project.models.DAO;
import ru.kpfu.itis.group501.krylov.db1_it_project.models.SimpleFilter;

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
        ParameterMap params = getParameterMap(req);
        addPathInfo(params, req);

        String tablename = params.get("table");
        if(tablename == null || tablename.equals("")) {
            //display main page
            Map<String, String> tables = Helpers.getTableTitles();
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("tables", tables);
            CommonHelpers.render(getServletContext(), req, resp, "admin/main.ftl", dataModel);
            return;
        }

        DAO<?> dao = Helpers.getDao(tablename);
        try {
            SimpleFilter filter = new SimpleFilter();
            filter.setLimit(10);
            if(params.containsKey("q"))
                filter.addAnyFieldLikeClause(tablename, params.get("q"));
            if(params.containsKey("sort")) {
                String key = params.get("sort");
                boolean desc = params.containsKey("sort_desc");
                filter.setOrder(key, !desc);
            }
            if(params.containsKey("page")) {
                filter.setOffset(params.getInt("page")*10-10);
            }
            List<Object[]> list = dao.getTable(filter);
            Map<String, Object> dataModel = new HashMap<>();

            dataModel.put("tablename", tablename);
            dataModel.put("rows", list);
            dataModel.put("columns", dao.getColumnNames());
            dataModel.put("params", params);

            CommonHelpers.render(getServletContext(), req, resp, "admin/table.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
