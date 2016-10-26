package app.servlets;

import app.Helpers;
import app.models.Feedbacks;
import app.models.Posts;
import app.models.User;
import app.models.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/24/16 12:18 PM
 */
public class ProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = request.getParameter("id") == null ? 0 : Integer.valueOf(request.getParameter("id"));

        try {
            User user = Users.get(id);
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("post_count", Posts.countPosts(user));
            dataModel.put("feedbacks", Feedbacks.getRecentFeedbacks(user, 5));
            dataModel.put("user", user);

            if(user.getUsername().equals(request.getSession().getAttribute("username"))) {
                dataModel.put("owner", true);
            }

            Helpers.render(getServletContext(), request, response, "profile.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            //todo 404
            e.printStackTrace();
        }
    }
}
