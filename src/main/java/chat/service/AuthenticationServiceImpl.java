package chat.service;

import chat.dao.UserDao;
import chat.exception.AuthenticationException;
import chat.lib.Inject;
import chat.lib.Service;
import chat.model.User;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserDao userDao;

    @Override
    public void isExisted(String name) throws AuthenticationException {
        Optional<User> user = userDao.findByName(name);
        if (user.isPresent()) {
            throw new AuthenticationException("This name exists. Enter a new one");
        }
    }
}
