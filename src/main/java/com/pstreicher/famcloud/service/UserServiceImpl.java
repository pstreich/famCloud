package com.pstreicher.famcloud.service;

import com.pstreicher.famcloud.domain.User;
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
    public User getUser(Authentication auth) {
        IDToken token = getKeycloakUserInfos(auth);
        if (token == null)
            return null;

        User user = userRepository.findByUsername(token.getPreferredUsername());
        if (user == null) {
            user = createNewUser(token);
            user = userRepository.save(user);
        }
        return user;
    }

    private User createNewUser(IDToken token) {
        User user = new User();
        user.setUsername(token.getPreferredUsername());
        user.setFirstName(token.getGivenName());
        user.setLastName(token.getFamilyName());
        user.setEmail(token.getEmail());

        return user;
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
