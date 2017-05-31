package ru.kpfu.itis.aekrylov.itproject.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kpfu.itis.aekrylov.itproject.NonWebTestProfile;
import ru.kpfu.itis.aekrylov.itproject.entities.Message;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.repositories.MessageRepository;
import ru.kpfu.itis.aekrylov.itproject.repositories.UserRepository;
import ru.kpfu.itis.aekrylov.itproject.services.ChatService;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/31/17 11:51 AM
 */

@RunWith(SpringRunner.class)
@NonWebTestProfile
public class ChatServiceTest {

    private ChatService chatService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MessageRepository messageRepository;

    private User alice = new User(1, "alice", "Alice", "alice@ex.com");
    private User bob = new User(2, "bob", "bob", "bob@ex.com");

/*
    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
*/

    @Before
    public void setUp() {
        chatService = new ChatService(messageRepository, userRepository);
        when(userRepository.findOne(1)).thenReturn(alice);
        when(userRepository.findOne(2)).thenReturn(bob);

/*
        messageRepository.save(new Message(alice, bob, "A->B", Timestamp.from(Instant.now())));
        messageRepository.save(new Message(bob, alice, "B->A", Timestamp.from(Instant.now().plusSeconds(5))));
*/
    }

    @Test
    public void testSendMessage() {

        Message example = new Message(alice, bob, "text");
        chatService.sendMessage(example.getFrom(), example.getTo().getId(), example.getText());

        ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
        verify(messageRepository).save(captor.capture());

        Message actual = captor.getValue();
        assertEquals(example.getFrom(), actual.getFrom());
        assertEquals(example.getTo(), actual.getTo());
        assertEquals(example.getText(), actual.getText());
    }

}
