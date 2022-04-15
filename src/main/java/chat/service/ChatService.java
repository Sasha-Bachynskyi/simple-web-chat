package chat.service;

import chat.model.Chat;
import chat.model.User;
import java.util.List;

public interface ChatService {
    List<Chat> getAll();

    Chat addUserToChat(User user, Chat chat);

    Chat get(Long id);

    void deleteUsersFromChat(Long id);

    boolean isUserPresent(Chat chat, User user);
}
