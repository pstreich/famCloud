package com.pstreicher.famcloud.controller;

import com.pstreicher.famcloud.dto.UserInfoDTO;
import com.pstreicher.famcloud.service.HobbyRadarService;
import com.pstreicher.famcloud.service.UserService;
import com.pstreicher.famcloud.util.AuthUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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
    HobbyRadarService hobbyRadarService;

    @Mock
    Model model;

    @Mock
    Authentication auth;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        baseController = new BaseController(userService, hobbyRadarService);
    }

    @Test
    public void mockMVC() throws Exception {
        try (MockedStatic<AuthUtil> mocked = mockStatic(AuthUtil.class)) {
            //given
            boolean isAuthZ = true;
            mocked.when(AuthUtil::isAuthorized).thenReturn(isAuthZ);
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(baseController).build();

            //when;then
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("index"));
        }
    }

    @Test
    void indexWithValidAuth() {
        try (MockedStatic<AuthUtil> mocked = mockStatic(AuthUtil.class)) {
            //given
            boolean isAuthZ = true;
            mocked.when(AuthUtil::isAuthorized).thenReturn(isAuthZ);

            //when
            String viewName = baseController.index(model);

            //then
            assertEquals("index", viewName);
            verify(model).addAttribute("isAuthZ", isAuthZ);
        }
    }

    @Test
    void indexWithInvalidAuth() {
        try (MockedStatic<AuthUtil> mocked = mockStatic(AuthUtil.class)) {
            //given
            boolean isAuthZ = false;
            mocked.when(AuthUtil::isAuthorized).thenReturn(isAuthZ);

            //when
            String viewName = baseController.index(model);

            //then
            assertEquals("index",viewName);
            verify(model).addAttribute("isAuthZ", isAuthZ);
        }
    }

    @Test
    void profile() {
        try (MockedStatic<AuthUtil> mocked = mockStatic(AuthUtil.class)) {
            //given
            UserInfoDTO userInfo = new UserInfoDTO();
            when(userService.getUserInfoDTO(auth))
                    .thenReturn(userInfo);
            mocked.when(AuthUtil::isAuthorized).thenReturn(true);

            //when
            String viewName = baseController.profile(auth, model);

            //then
            assertEquals("profile", viewName);
            verify(model).addAttribute("userInfo", userInfo);
        }
    }
}