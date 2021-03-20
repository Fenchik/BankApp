package ru.maximen.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maximen.dao.UserDao;
import ru.maximen.entity.User;

@Service
@ComponentScan("ru.maximen")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) {
        User user = userDao.findByLogin(login);

        logger.info("User = " + user.toString());

        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        logger.info("User = " + new MyUserPrincipal(user).getUsername());
        logger.info("User = " + new MyUserPrincipal(user).getAuthorities());

        return new MyUserPrincipal(user);
    }

}
