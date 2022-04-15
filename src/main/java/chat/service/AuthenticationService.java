package chat.service;

import chat.exception.AuthenticationException;

public interface AuthenticationService {
    void isExisted(String name) throws AuthenticationException;
}
