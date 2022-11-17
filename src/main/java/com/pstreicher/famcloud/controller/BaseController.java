package com.pstreicher.famcloud.controller;

import com.pstreicher.famcloud.domain.UserInfo;
import com.pstreicher.famcloud.service.BaseService;
import com.pstreicher.famcloud.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {

    private final BaseService baseService;
    private final UserService userService;

    public BaseController(BaseService baseService, UserService userService) {
        this.baseService = baseService;
        this.userService = userService;
    }

    @RequestMapping(value = {"/access-denied"})
    public String accessDenied() {
        return ("access-denied");
    }

    @RequestMapping(value = {"/logout"})
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "logged-out";
    }

    @RequestMapping("/contact-us")
    public String getContactUs() {
        return "contact-us";
    }

    @RequestMapping({"", "/"})
    public String index(Model model) {
        boolean isAuthZ = baseService.isAuthorized();
        model.addAttribute("isAuthZ", isAuthZ);
        return "index";
    }

    @RequestMapping( "/home")
    public String getHome() {
        return "home";
    }

    @RequestMapping( "/profile")
    public String profile(Authentication auth, Model model) {
        UserInfo userInfo = userService.getUser(auth);
        model.addAttribute("userInfo", userInfo);
        return "profile";
    }

}
