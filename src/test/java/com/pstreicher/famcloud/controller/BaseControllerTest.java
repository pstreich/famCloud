package com.pstreicher.famcloud.controller;

import com.pstreicher.famcloud.domain.UserInfo;
import com.pstreicher.famcloud.service.BaseService;
import com.pstreicher.famcloud.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BaseControllerTest {

    BaseController baseController;

    @Mock
    UserService userService;

    @Mock
    BaseService baseService;

    @Mock
    Model model;

    @Mock
    Authentication auth;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        baseController = new BaseController(baseService, userService);
    }

    @Test
    void indexWithValidAuth() {
        when(baseService.isAuthorized()).thenReturn(true);
        assertEquals("index", baseController.index(model));
        verify(model, times(1)).addAttribute("isAuthZ", true);
    }

    @Test
    void indexWithInvalidAuth() {
        when(baseService.isAuthorized()).thenReturn(false);
        assertEquals("index", baseController.index(model));
        verify(model, times(1)).addAttribute("isAuthZ", false);
    }

    @Test
    void profile() {
        when(userService.getUser(auth)).thenReturn(new UserInfo());
        assertEquals("profile", baseController.profile(auth, model));
        verify(model, times(1)).addAttribute("userInfo", new UserInfo());
    }
}