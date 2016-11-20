package ru.kpfu.itis.group501.krylov.db1_it_project.servlets;

import ru.kpfu.itis.group501.krylov.db1_it_project.Helpers;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Post;
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
        Map<String, String > map = getParameterMap(req);
        Map<String, Object> dataModel = new HashMap<>();

        if(map.get("filtered") != null) {
            //todo more filters
            dataModel.put("filtered", true);
            filter.addSignClause("type", "=", Helpers.getString(map, "type"));
            filter.addLikeClause("brand", Helpers.getString(map, "brand"));
            filter.addLikeClause("model", Helpers.getString(map, "model"));
            filter.addSignClause("price", ">", Helpers.getInt(map, "price_low"));
            filter.addSignClause("price", "<", Helpers.getInt(map, "price_high"));
            filter.addSignClause("cores", "=", Helpers.getInt(map, "cores"));
        }
        if(map.get("author") != null) {
            dataModel.put("filtered", true);
            filter.addSignClause("user", "=", Integer.parseInt(map.get("author")));
        }

        try {
            List<Post> posts = postService.getPosts(filter);
            dataModel.put("posts", posts);
            dataModel.put("params", map);

            Helpers.render(getServletContext(), req, resp, "product_list.ftl", dataModel);

        } catch (SQLException e) {
            //todo
            e.printStackTrace();
        }
    }
}
