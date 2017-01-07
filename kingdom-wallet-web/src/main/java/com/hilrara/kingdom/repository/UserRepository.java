package com.hilrara.kingdom.repository;


import com.hilrara.kingdom.hibernate.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by tommy on 2016. 2. 29..
 */
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);

    User findOneByEmailAndPasswordHash(String email, String passwordHash);
}
