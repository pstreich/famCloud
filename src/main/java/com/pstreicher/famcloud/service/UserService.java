package com.pstreicher.famcloud.service;

import com.pstreicher.famcloud.domain.User;
import org.springframework.security.core.Authentication;

public interface UserService {
    User getUser(Authentication auth);
}
