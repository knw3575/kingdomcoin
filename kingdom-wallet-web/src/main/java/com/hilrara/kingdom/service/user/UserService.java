package com.hilrara.kingdom.service.user;


import com.hilrara.kingdom.hibernate.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by tommy on 2016. 2. 29..
 */
public interface UserService {
    Optional<User> getUserByEmail(String email);
    public Optional<User> getUserById(long id);
    Collection<User> getAllUsers();
}
