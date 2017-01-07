package com.hilrara.kingdom.hibernate;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by tommy on 2016. 2. 29..
 */
@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 60)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 60)
    private String passwordHash;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public String toString() {
        return "User{" +
                ", email='" + email.replaceFirst("@.*", "@***") +
                ", passwordHash='" + passwordHash.substring(0, 10) +
                ", role=" + role +
                '}';
    }
}
