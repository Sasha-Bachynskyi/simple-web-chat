package chat.service;

import chat.dao.ChatDao;
import chat.lib.Inject;
import chat.lib.Service;
import chat.model.Chat;
import chat.model.User;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Inject
    private ChatDao chatDao;

    @Override
    public List<Chat> getAll() {
        return chatDao.getAll();
    }

    @Override
    public Chat addUserToChat(User user, Chat chat) {
        chat.getUsers().add(user);
        return chatDao.update(chat);
    }

    @Override
    public Chat get(Long id) {
        return chatDao.get(id).orElseThrow(() -> new RuntimeException("There is no chat in DB by such id: " + id));
    }

    @Override
    public void deleteUsersFromChat(Long id) {
        Chat chat = get(id);
        chatDao.deleteUsersFromChat(chat);
    }

    @Override
    public boolean isUserPresent(Chat chat, User user) {
        return chatDao.isUserPresent(chat, user);
    }
}
