package app.servlets;

import app.Helpers;
import app.models.User;

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
            User user = Helpers.getCurrentUser(req);

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("sells", feedbackService.getRecentSells(user));
            dataModel.put("buys", feedbackService.getRecentBuys(user));

            Helpers.render(getServletContext(), req, resp, "history.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        Map<String, String> map = getParameterMap(req);
        int id = Integer.parseInt(map.get("id"));
        String text = map.get("text");
        int rating = Integer.parseInt(map.get("rating"));

        try {
            feedbackService.leaveFeedback(id, rating, text);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        redirect(req, resp);
    }
}
