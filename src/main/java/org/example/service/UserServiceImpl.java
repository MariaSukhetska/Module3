package org.example.service;

import org.example.dao.UserDao;
import org.example.library.Inject;
import org.example.library.Service;
import org.example.model.User;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User add(User user) {
        return userDao.add(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }
}
