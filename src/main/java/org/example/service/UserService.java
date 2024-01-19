package org.example.service;

import org.example.model.User;
import java.util.Optional;

public interface UserService {
    User add(User user);

    void update(User user);

    void delete(User user);

    Optional<User> findById(Long id);
}
