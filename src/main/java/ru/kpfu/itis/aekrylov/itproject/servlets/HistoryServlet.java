package ru.kpfu.itis.aekrylov.itproject.servlets;

import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.misc.ParameterMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/18/16 2:52 PM
 */
public class HistoryServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        //get recent buy_sells
        try {
            User user = CommonHelpers.getCurrentUser();

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("sells", feedbackService.getRecentSells(user));
            dataModel.put("buys", feedbackService.getRecentBuys(user));

            CommonHelpers.render(getServletContext(), req, resp, "history.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        ParameterMap map = getParameterMap(req);
        int id = Integer.parseInt(map.get("id"));
        String text = map.get("text");
        int rating = map.getInt("rating");

        try {
            feedbackService.leaveFeedback(id, rating, text);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        redirect(req, resp);
    }
}
