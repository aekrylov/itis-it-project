package ru.kpfu.itis.group501.krylov.db1_it_project.servlets;

import ru.kpfu.itis.group501.krylov.db1_it_project.misc.CommonHelpers;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Post;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.ParameterMap;
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
 * Date: 11/15/16 5:11 PM
 */
public class ItemListServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        SimpleFilter filter = new SimpleFilter();
        ParameterMap map = getParameterMap(req);
        Map<String, Object> dataModel = new HashMap<>();

        if(map.get("filtered") != null) {
            //todo more filters
            dataModel.put("filtered", true);
            filter.addSignClause("type", "=", map.get("type"));
            filter.addLikeClause("brand", map.get("brand"));
            filter.addLikeClause("model", map.get("model"));
            filter.addSignClause("price", ">", map.getInt("price_low"));
            filter.addSignClause("price", "<", map.getInt("price_high"));
            filter.addSignClause("cores", "=", map.getInt("cores"));
        }
        if(map.get("author") != null) {
            dataModel.put("filtered", true);
            filter.addSignClause("user", "=", map.getInt("author"));
        }

        try {
            List<Post> posts = postService.getPosts(filter);
            dataModel.put("posts", posts);
            dataModel.put("params", map);

            CommonHelpers.render(getServletContext(), req, resp, "product_list.ftl", dataModel);

        } catch (SQLException e) {
            //todo
            e.printStackTrace();
        }
    }
}
