package com.hilrara.kingdom.service.user;

import com.hilrara.kingdom.hibernate.User;
import com.hilrara.kingdom.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by tommy on 2016. 2. 29..
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserById(long id) {
        LOGGER.info("Getting user={}", id);
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        LOGGER.info("Getting user by email={}", email.replaceFirst("@.*", "@***"));

        Optional<User> user = userRepository.findOneByEmail(email);
        return user;
    }

    @Override
    public Collection<User> getAllUsers() {
        LOGGER.info("Getting all users");
        return userRepository.findAll(new Sort("email"));
    }
}
