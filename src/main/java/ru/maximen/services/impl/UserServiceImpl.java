package ru.maximen.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maximen.dao.UserDao;
import ru.maximen.entity.User;
import ru.maximen.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    @Transactional
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}
