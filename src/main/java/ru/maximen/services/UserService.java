package ru.maximen.services;

import ru.maximen.entity.User;


public interface UserService {

    User findByLogin(String login);
}
