package ru.kpfu.itis.aekrylov.itproject.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kpfu.itis.aekrylov.itproject.NoContextTestProfile;
import ru.kpfu.itis.aekrylov.itproject.NonWebTestProfile;
import ru.kpfu.itis.aekrylov.itproject.entities.BuySell;
import ru.kpfu.itis.aekrylov.itproject.entities.Feedback;
import ru.kpfu.itis.aekrylov.itproject.entities.Product;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.repositories.BuySellRepository;
import ru.kpfu.itis.aekrylov.itproject.repositories.FeedbackRepository;
import ru.kpfu.itis.aekrylov.itproject.repositories.UserRepository;
import ru.kpfu.itis.aekrylov.itproject.services.FeedbackService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/31/17 2:12 PM
 */

@NoContextTestProfile
@RunWith(SpringRunner.class)
public class FeedbackServiceTest {

    private FeedbackService feedbackService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FeedbackRepository feedbackRepository;

    @MockBean
    private BuySellRepository buySellRepository;

    private User seller;
    private User buyer;
    private Product product;
    private BuySell buySell;

    @Before
    public void setUp() {
        feedbackService = new FeedbackService(feedbackRepository, buySellRepository, userRepository);
        
        seller = new User(1, "seller", "Seller", "seller@ex.com");
        buyer = new User(2, "buyer", "Buyer", "buyer@ex.com");
        product = new Product();

        buySell = new BuySell(buyer, seller, product);
        buySell.setId(1);

        when(buySellRepository.findOne(anyInt())).thenReturn(buySell);
    }

    @Test
    public void testSavesFeedback() {
        Feedback example = new Feedback("text", 5);
        example.setBuySell(buySell);
        feedbackService.leaveFeedback(buySell.getId(), 5, "text");

        ArgumentCaptor<Feedback> captor = ArgumentCaptor.forClass(Feedback.class);
        verify(feedbackRepository).save(captor.capture());
        Feedback actual = captor.getValue();

        assertEquals(example, actual);
    }

    @Test
    public void testAddsRating() {
        seller.setRate_count(1);
        seller.setRating(5);

        feedbackService.leaveFeedback(buySell.getId(), 2, "text");
        assertEquals(3.5, seller.getRating(), 0.001);
        assertEquals(2, seller.getRate_count());
    }


}
