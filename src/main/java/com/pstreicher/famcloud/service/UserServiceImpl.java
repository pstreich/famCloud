package com.pstreicher.famcloud.service;

import com.pstreicher.famcloud.domain.UserInfo;
import com.pstreicher.famcloud.repository.UserRepository;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
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

    protected UserInfo createNewUser(IDToken token) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(token.getPreferredUsername());
        userInfo.setFirstName(token.getGivenName());
        userInfo.setLastName(token.getFamilyName());
        userInfo.setEmail(token.getEmail());

        return userInfo;
    }

    protected IDToken getKeycloakUserInfos(Authentication auth) {
        Principal principal = (Principal) auth.getPrincipal();
        IDToken token = null;
        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            token = kPrincipal.getKeycloakSecurityContext().getIdToken();
        }
        return token;
    }
}
