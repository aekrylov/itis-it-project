package app.services;

import app.models.Feedback;
import app.models.Feedbacks;
import app.models.User;

import java.sql.SQLException;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/18/16 12:33 AM
 */
public class FeedbackService {

    private static FeedbackService instance = new FeedbackService();
    public static FeedbackService getInstance() {
        return instance;
    }
    private FeedbackService() {}

    private static Feedbacks feedbacks = new Feedbacks();

    public List<Feedback> getRecentFeedbacks(User seller, int limit) throws SQLException {
        return feedbacks.getRecentFeedbacks(seller, limit);
    }
}
