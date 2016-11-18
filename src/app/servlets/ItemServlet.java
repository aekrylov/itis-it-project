package app.servlets;

import app.Helpers;
import app.entities.Post;
import app.entities.User;
import app.services.PostService;

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

    private PostService postService = PostService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            Post post = postService.getPost(id);
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("isSeller", post.getUser().getId() == Helpers.getCurrentUser(req).getId());
            dataModel.put("post", post);
            dataModel.put("product", post.getProduct());

            Helpers.render(getServletContext(), req, resp, "product.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        Map<String, String> map = getParameterMap(req);
        String action = map.get("action");

        int postId = Integer.parseInt(map.get("post_id"));

        try {
            switch (action) {
                case "sell":
                    User buyer = userService.get(map.get("buyer"));
                    User seller = Helpers.getCurrentUser(req);

                    postService.sellProduct(seller, buyer, postId);
                    resp.sendRedirect("/items");
                    return;

                case "delete":
                    if(postService.deletePost(postId)) {
                        resp.sendRedirect("/items");
                    } else {
                        redirect(req, resp);
                    }
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            redirect(req, resp);
        }
    }
}
