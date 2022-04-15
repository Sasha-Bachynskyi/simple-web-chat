package chat.service;

import chat.model.User;

public interface UserService {
    User create(User user);

    User get(Long id);
}
