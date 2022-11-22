package com.pstreicher.famcloud.service;

import com.pstreicher.famcloud.domain.UserInfo;
import com.pstreicher.famcloud.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Base64;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserInfo getUser(Authentication auth) {
        IDToken token = getKeycloakUserInfos(auth);
        if (token == null)
            return null;

        UserInfo userInfo = userRepository.findByUsername(token.getPreferredUsername());
        if (userInfo == null) {
            userInfo = createNewUser(token);
            userInfo = userRepository.save(userInfo);
        }
        return userInfo;
    }

    public UserInfo createNewUser(IDToken token) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(token.getPreferredUsername());
        userInfo.setFirstName(token.getGivenName());
        userInfo.setLastName(token.getFamilyName());
        userInfo.setEmail(token.getEmail());

        return userInfo;
    }

    @Override
    public UserInfo getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserInfo updateProfileImage(String username, String b64Image) {
        UserInfo userInfo = getUserByUsername(username);
        if (userInfo == null) {
            log.error("cannot find user with username: " + username);
            return null;
        } else {
            userInfo.setProfileImage(Base64.getDecoder().decode(b64Image));
            userInfo = userRepository.save(userInfo);
            return userInfo;
        }
    }

    protected IDToken getKeycloakUserInfos(Authentication auth) {
        Principal principal = (Principal) auth.getPrincipal();
        IDToken token = null;
        if (principal instanceof KeycloakPrincipal) {
            @SuppressWarnings("unchecked")
            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            token = kPrincipal.getKeycloakSecurityContext().getIdToken();
        }
        return token;
    }
}
