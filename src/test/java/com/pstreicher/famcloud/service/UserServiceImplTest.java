package com.pstreicher.famcloud.service;

import com.pstreicher.famcloud.domain.UserInfo;
import com.pstreicher.famcloud.repository.UserRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.IDToken;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


//@SpringBootTest
class UserServiceImplTest {

    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Authentication auth;

    @Mock
    KeycloakPrincipal<KeycloakSecurityContext> kPrincipal;

    static IDToken idTokenFixture;
    static UserInfo userInfoFixture;
    static KeycloakPrincipal<KeycloakSecurityContext> principalFixture;

    @BeforeAll
    public static void beforAll() {
        idTokenFixture = createIdTokenFixture();
        principalFixture = createPrincipalFixture();
        userInfoFixture = createUserInfoFixture();
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void getUser() {
        when(auth.getPrincipal()).thenReturn(principalFixture);
        when(userRepository.save(any())).thenReturn(userInfoFixture);

        assertEquals(userInfoFixture,  userService.getUser(auth));
    }

    @Test
    void createNewUser() {
        assertEquals(userInfoFixture, userService.createNewUser(idTokenFixture));
    }

    private static UserInfo createUserInfoFixture() {
        return new UserInfo().builder()
                .username("username")
                .firstName("John")
                .lastName("Thompson")
                .email("jthompson@example.com")
                .build();
    }

    private static KeycloakPrincipal<KeycloakSecurityContext> createPrincipalFixture() {
        KeycloakSecurityContext keycloakSecurityContext = new KeycloakSecurityContext(null, null, null, idTokenFixture);
        return new KeycloakPrincipal<>("John", keycloakSecurityContext);
    }

    private static IDToken createIdTokenFixture() {
        IDToken idToken = new IDToken();
        idToken.setPreferredUsername("username");
        idToken.setGivenName("John");
        idToken.setFamilyName("Thompson");
        idToken.setEmail("jthompson@example.com");

        return idToken;
    }
}