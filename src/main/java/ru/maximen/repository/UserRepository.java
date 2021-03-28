package ru.maximen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maximen.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String username);
}
