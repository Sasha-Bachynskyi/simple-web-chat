package chat.dao;

import chat.model.Chat;
import chat.model.User;

import java.util.List;
import java.util.Optional;

public interface ChatDao {
    List<Chat> getAll();

    Chat update(Chat chat);

    Optional<Chat> get(Long id);

    void deleteUsersFromChat(Chat chat);

    boolean isUserPresent(Chat chat, User user);
}
