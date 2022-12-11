package com.pstreicher.famcloud.service;

import com.pstreicher.famcloud.domain.UserInfo;
import com.pstreicher.famcloud.dto.UserInfoDTO;
import com.pstreicher.famcloud.mapper.UserInfoMapper;
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

class UserServiceImplTest {

    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Authentication auth;

//    @Mock
//    UserInfoMapper userInfoMapper;

    static IDToken idTokenFixture;
    static UserInfoDTO userInfoDTOFixture;
    static UserInfo userInfoFixture;
    static KeycloakPrincipal<KeycloakSecurityContext> principalFixture;

    @BeforeAll
    public static void beforAll() {
        idTokenFixture = createIdTokenFixture();
        principalFixture = createPrincipalFixture();
        userInfoFixture = createUserInfoFixture();
        userInfoDTOFixture = createUserInfoDTOFixture();
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

        assertEquals(userInfoDTOFixture,  userService.getUserInfoDTO(auth));
    }

    @Test
    void createNewUser() {
        assertEquals(userInfoDTOFixture, UserInfoMapper.MAPPER.userInfoToUserInfoDTO(userService.createNewUser(idTokenFixture)));
    }

    private static UserInfoDTO createUserInfoDTOFixture() {
        return UserInfoDTO.builder()
                .username("username")
                .firstName("John")
                .lastName("Thompson")
                .email("jthompson@example.com")
                .build();
    }

    private static UserInfo createUserInfoFixture() {
        return UserInfo.builder()
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