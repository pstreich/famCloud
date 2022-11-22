package com.pstreicher.famcloud.service;

import com.pstreicher.famcloud.domain.UserInfo;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.Authentication;

public interface UserService {
    UserInfo getUserByUsername(String username);

    UserInfo getUser(Authentication auth);

    UserInfo createNewUser(IDToken idToken);
    UserInfo updateProfileImage (String username, String b64Image);


}
