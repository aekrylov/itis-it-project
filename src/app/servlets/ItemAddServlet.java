package app.servlets;

import app.Helpers;
import app.models.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/2/16 11:04 AM
 */
public class ItemAddServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        //render form
        Helpers.render(getServletContext(), req, resp, "new_product.ftl", null);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        //add product and redirect to its page
        Map<String, String> map = getParameterMap(req);

        try {
            Product product = postService.toProduct(map);
            postService.createProduct(product);
            Post post = new Post(product, Helpers.getCurrentUser(req));
            postService.createPost(post);

            resp.sendRedirect("/item?id="+post.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
