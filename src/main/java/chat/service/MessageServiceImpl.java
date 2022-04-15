package chat.service;

import chat.dao.MessageDao;
import chat.lib.Inject;
import chat.lib.Service;
import chat.model.Message;
import java.util.Collections;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Inject
    private MessageDao messageDao;

    @Override
    public Message create(Message message) {
        return messageDao.create(message);
    }

    @Override
    public List<Message> getLastTenMessages() {
        List<Message> lastTenMessages = messageDao.getLastTenMessages();
        Collections.reverse(lastTenMessages);
        return lastTenMessages;
    }
}
