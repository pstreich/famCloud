package com.pstreicher.famcloud.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class AuthUtilTest {

    @Mock
    Authentication auth;

    @Mock
    SecurityContext securityContext;

    @Mock
    AnonymousAuthenticationToken anonymousAuthenticationToken;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void isAuthorized() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            //given
            boolean isAuthZExpected = true;
            mocked.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(auth);
            when(auth.isAuthenticated()).thenReturn(true);

            //when
            boolean isAuthZ = AuthUtil.isAuthorized();

            //then
            assertEquals(isAuthZExpected, isAuthZ);
        }
    }

    @Test
    void isNotAuthorized() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            //given
            boolean isAuthZExpected = false;
            mocked.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(auth);
            when(auth.isAuthenticated()).thenReturn(false);

            //when
            boolean isAuthZ = AuthUtil.isAuthorized();

            //then
            assertEquals(isAuthZExpected, isAuthZ);
        }
    }

    @Test
    void isAnonymous() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            //given
            boolean isAuthZExpected = false;
            mocked.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(anonymousAuthenticationToken);
            when(anonymousAuthenticationToken.isAuthenticated()).thenReturn(true);

            //when
            boolean isAuthZ = AuthUtil.isAuthorized();

            //then
            assertEquals(isAuthZExpected, isAuthZ);
        }
    }
}