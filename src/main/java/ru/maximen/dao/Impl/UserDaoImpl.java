package ru.maximen.dao.Impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.maximen.dao.UserDao;
import ru.maximen.entity.User;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Override
    public User findByLogin(String login) {
        return sessionFactory.getCurrentSession()
                .createNativeQuery("select * from users where login = :login", User.class)
                .setParameter("login",login)
                .getResultList()
                .get(0);
    }
}
