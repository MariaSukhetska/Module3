package org.example.dao;

import org.example.model.User;
import java.util.Optional;

public interface UserDao {
    User add(User user);

    void update(User user);

    void delete(User user);

    Optional<User> findById(Long id);
}
