package com.pstreicher.famcloud.service;

import com.pstreicher.famcloud.domain.UserInfo;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

//    @Before
//    void setup() {
//        UserInfo userInfoJohn = new UserInfo();
//    }

    @Test
    void createNewUser() {
        IDToken idToken = new IDToken();
        idToken.setPreferredUsername("username");
        idToken.setGivenName("John");
        idToken.setFamilyName("Thompson");
        idToken.setEmail("jthompson@example.com");

        UserInfo userInfo = new UserInfo().builder()
                    .username("username")
                    .firstName("John")
                    .lastName("Thompson")
                    .email("jthompson@example.com")
                    .build();

        assertEquals(userInfo, userService.createNewUser(idToken));
    }
}