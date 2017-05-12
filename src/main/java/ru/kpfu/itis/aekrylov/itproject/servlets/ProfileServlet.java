package ru.kpfu.itis.aekrylov.itproject.servlets;

import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.misc.NotFoundException;
import ru.kpfu.itis.aekrylov.itproject.entities.User;

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
                user = CommonHelpers.getCurrentUser();
            } else {
                user = userService.get(Integer.parseInt(request.getParameter("id")));
            }
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("post_count", postService.countPosts(user));
            dataModel.put("buy_sells", feedbackService.getRecentFeedbacks(user));
            dataModel.put("user", user);

            if(user.getUsername().equals(request.getSession().getAttribute("username"))) {
                dataModel.put("owner", true);
            }

            CommonHelpers.render(getServletContext(), request, response, "profile.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500);
/*
        } catch (NotFoundException e) {
            response.sendError(404);
*/
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        Part image = req.getPart("image");
        try {
            User user = CommonHelpers.getCurrentUser();
            CommonHelpers.saveImage(Paths.get("users", String.valueOf(user.getId())),
                    image.getInputStream());
            user.setHas_avatar(true);
            userService.updateInfo(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        redirect(req, resp);

    }
}
