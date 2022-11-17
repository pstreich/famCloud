package com.pstreicher.famcloud.controller;

import com.pstreicher.famcloud.domain.UserInfo;
import com.pstreicher.famcloud.service.BaseService;
import com.pstreicher.famcloud.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
    public void mockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(baseController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void indexWithValidAuth() {
        //given
        boolean isAuthZ = true;
        when(baseService.isAuthorized()).thenReturn(isAuthZ);

        //when
        String viewName = baseController.index(model);

        //then
        assertEquals("index", viewName);
        verify(model).addAttribute("isAuthZ", isAuthZ);
    }

    @Test
    void indexWithInvalidAuth() {
        //given
        boolean isAuthZ = false;
        when(baseService.isAuthorized()).thenReturn(isAuthZ);

        //when
        String viewName = baseController.index(model);

        //then
        assertEquals("index",viewName);
        verify(model).addAttribute("isAuthZ", isAuthZ);
    }

    @Test
    void profile() {
        //given
        UserInfo userInfo = new UserInfo();
        when(userService.getUser(auth)).thenReturn(userInfo);

        //when
        String viewName = baseController.profile(auth, model);

        //then
        assertEquals("profile", viewName);
        verify(model).addAttribute("userInfo", userInfo);
    }
}