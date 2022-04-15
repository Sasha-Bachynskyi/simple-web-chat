package chat.service;

import chat.dao.UserDao;
import chat.lib.Inject;
import chat.lib.Service;
import chat.model.User;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).orElseThrow(() -> new RuntimeException("There is no user in DB by such id: " + id));
    }
}
