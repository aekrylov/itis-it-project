package app.servlets;

import app.Helpers;
import app.models.Post;
import app.models.Posts;
import app.models.SimpleFilter;

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
        if(map.get("filtered") != null) {
            //todo more filters
            filter.addLikeClause("brand", Helpers.getString(map, "brand"));
            filter.addLikeClause("model", Helpers.getString(map, "model"));
            filter.addSignClause("price", ">", Helpers.getInt(map, "price_low"));
            filter.addSignClause("price", "<", Helpers.getInt(map, "price_high"));
            filter.addSignClause("cores", "=", Helpers.getInt(map, "cores"));
        }

        try {
            List<Post> posts = postService.getPost(filter);
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("posts", posts);

            Helpers.render(getServletContext(), req, resp, "product_list.ftl", dataModel);

        } catch (SQLException e) {
            //todo
            e.printStackTrace();
        }
    }
}
