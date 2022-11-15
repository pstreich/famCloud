package com.pstreicher.famcloud.service;

import com.pstreicher.famcloud.domain.UserInfo;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserInfo getUser(Authentication auth);
}
