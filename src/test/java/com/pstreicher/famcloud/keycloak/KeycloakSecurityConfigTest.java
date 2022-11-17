package com.pstreicher.famcloud.keycloak;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KeycloakSecurityConfigTest {

    KeycloakSecurityConfig keycloakSecurityConfig;

    @Autowired
    private ApplicationContext context;

    @Test
    void configTest() {
        keycloakSecurityConfig = new KeycloakSecurityConfig();
        String xy = "";
    }

}