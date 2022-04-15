package chat.dao;

import chat.model.Message;

import java.util.List;

public interface MessageDao {
    Message create(Message message);

    List<Message> getLastTenMessages();
}
