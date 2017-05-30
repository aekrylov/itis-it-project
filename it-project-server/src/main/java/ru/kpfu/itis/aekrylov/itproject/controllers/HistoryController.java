package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.forms.BuySellFeedbackForm;
import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.services.FeedbackService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/25/17 10:34 PM
 */

@Controller
@RequestMapping("/user/history")
public class HistoryController {

    private FeedbackService feedbackService;

    @Autowired
    public HistoryController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    protected String doGet(ModelMap dataModel) {
        //get recent buy_sells
        User user = CommonHelpers.getCurrentUser();

        dataModel.put("sells", feedbackService.getRecentSells(user));
        dataModel.put("buys", feedbackService.getRecentBuys(user));

        return "history";
    }

    @PostMapping
    protected String doPost(BuySellFeedbackForm form) {
        feedbackService.leaveFeedback(form.getBsid(), form.getRating(), form.getText());
        return "redirect:/user/history";
    }
}
