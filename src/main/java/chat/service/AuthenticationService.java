package chat.service;

import chat.exception.AuthenticationException;
import chat.model.User;

public interface AuthenticationService {
    void isExisted(String name) throws AuthenticationException;
}
