package ru.maximen.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maximen.entity.User;
import ru.maximen.repository.UserRepository;

@Slf4j
@Service
@ComponentScan("ru.maximen")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userRepository.findByLogin(login);

        log.info("User = " + user.toString());

        if (user == null) {
            throw new UsernameNotFoundException(login);
        }
        log.info("User = " + new MyUserPrincipal(user).getUsername());
        log.info("User = " + new MyUserPrincipal(user).getAuthorities());

        return new MyUserPrincipal(user);
    }

}
