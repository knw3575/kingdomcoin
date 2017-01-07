package com.hilrara.kingdom.hibernate;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by tommy on 2016. 2. 29..
 */
public interface UserDetails extends Serializable {
    Collection<? extends GrantedAuthority> getAuthorities();

    Long getId();

    String getPassword();

    String getUsername();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();

    boolean isEnabled();
}

