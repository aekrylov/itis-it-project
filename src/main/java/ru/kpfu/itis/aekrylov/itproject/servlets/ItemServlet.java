package ru.kpfu.itis.aekrylov.itproject.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.entities.Post;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.misc.NotFoundException;
import ru.kpfu.itis.aekrylov.itproject.services.PostService;

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
@Component
public class ItemServlet extends BaseServlet {

    @Autowired
    private PostService postService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            Post post = postService.getPost(id);
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("isSeller", post.getUser().getId() == CommonHelpers.getCurrentUser().getId());
            dataModel.put("post", post);
            dataModel.put("product", post.getProduct());

            CommonHelpers.render(getServletContext(), req, resp, "product.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
            resp.sendError(404);
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
                    User seller = CommonHelpers.getCurrentUser();

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
        } catch (Exception e) {
            e.printStackTrace();
            redirect(req, resp);
        }
    }
}
