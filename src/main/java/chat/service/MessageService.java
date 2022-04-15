package chat.service;

import chat.model.Message;

import java.util.List;

public interface MessageService {
    Message create(Message message);

    List<Message> getLastTenMessages();
}
