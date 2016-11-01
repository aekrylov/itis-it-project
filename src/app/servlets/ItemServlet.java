package app.servlets;

import app.Helpers;
import app.models.Post;
import app.models.Posts;
import app.models.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/1/16 10:36 PM
 */
public class ItemServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            Post post = Posts.get(id);
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("isSeller", post.getUser().getId() == Users.get(Helpers.getUsername(req)).getId());
            dataModel.put("post", post);
            dataModel.put("product", post.getProduct());

            Helpers.render(getServletContext(), req, resp, "product.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
