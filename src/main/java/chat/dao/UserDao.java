package chat.dao;

import chat.model.User;

import java.util.Optional;

public interface UserDao {
    User create(User user);

    Optional<User> findByName(String name);

    Optional<User> get(Long id);
}
