package ru.maximen.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.maximen.entity.User;
import ru.maximen.repository.UserRepository;
import ru.maximen.services.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findByLogin() {

        User user = new User();

        String login = "user";

        when(userRepository.findByLogin(login)).thenReturn(user);

        assertEquals(userService.findByLogin(login), user);
    }
}