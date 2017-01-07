package com.hilrara.kingdom.controller.advice;

import com.hilrara.kingdom.hibernate.CurrentUser;
import com.hilrara.kingdom.hibernate.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by tommy on 2016. 2. 29..
 */
@ControllerAdvice
public class CurrentUserControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserControllerAdvice.class);

    @ModelAttribute("user")
    public User getCurrentUser(Authentication authentication/*, @RequestBody String payload*/) {
        return (authentication == null) ? null : ((CurrentUser) authentication.getPrincipal()).getUser();
    }
}
