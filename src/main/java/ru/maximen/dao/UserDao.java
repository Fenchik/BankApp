package ru.maximen.dao;

import ru.maximen.entity.User;

public interface UserDao {

    User findByLogin(String login);


}
