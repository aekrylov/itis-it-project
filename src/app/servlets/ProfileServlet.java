package app.servlets;

import app.Helpers;
import app.misc.NotFoundException;
import app.models.Posts;
import app.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 12:18 PM
 */
@MultipartConfig
public class ProfileServlet extends BaseServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user;
            if(request.getParameter("id") == null) {
                user = Helpers.getCurrentUser(request);
            } else {
                user = userService.get(Integer.parseInt(request.getParameter("id")));
            }
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("post_count", Posts.countPosts(user));
            dataModel.put("buy_sells", feedbackService.getRecentFeedbacks(user));
            dataModel.put("user", user);

            if(user.getUsername().equals(request.getSession().getAttribute("username"))) {
                dataModel.put("owner", true);
            }

            Helpers.render(getServletContext(), request, response, "profile.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
        } catch (NotFoundException e) {
            response.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        //todo process image
        Part image = req.getPart("image");
        try {
            Helpers.saveImage(Paths.get("users", String.valueOf(Helpers.getCurrentUser(req).getId())),
                    image.getInputStream());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        redirect(req, resp);

    }
}
