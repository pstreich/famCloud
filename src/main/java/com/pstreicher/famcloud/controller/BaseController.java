package com.pstreicher.famcloud.controller;

import com.pstreicher.famcloud.domain.AvatarFormCommand;
import com.pstreicher.famcloud.domain.UserInfo;
import com.pstreicher.famcloud.service.UserService;
import com.pstreicher.famcloud.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Controller
@Slf4j
public class BaseController {

    private final UserService userService;

    public BaseController(UserService userService) {
        this.userService = userService;
    }

//    @RequestMapping(value = {"/access-denied"})
//    public String accessDenied() {
//        return ("access-denied");
//    }

    @RequestMapping( "/sso/login")
    public String login() {
        log.warn("in sso/login Endpoint");
        return "redirect:profile";
    }

    @RequestMapping(value = {"/logout"})
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "logged-out";
    }

    @RequestMapping({"", "/"})
    public String index(Model model) {
        boolean isAuthZ = AuthUtil.isAuthorized();
        model.addAttribute("isAuthZ", isAuthZ);
        return "index";
    }

//    @RequestMapping( "/home")
//    public String getHome() {
//        return "home";
//    }

    @RequestMapping( "/profile")
    public String profile(Authentication auth, Model model) {
        UserInfo userInfo = userService.getUser(auth);
        model.addAttribute("userInfo", userInfo);
        if (userInfo.getProfileImage() != null) {
            model.addAttribute("userAvatar", Base64.getEncoder().encodeToString(userInfo.getProfileImage()));
        }
        model.addAttribute("avatar", new AvatarFormCommand());
        return "profile";
    }

    @PostMapping( "/avatar-store")
    public String storeAvatar(@ModelAttribute AvatarFormCommand avatar, BindingResult bindingResult, Model model) {
        model.addAttribute("avatar", avatar);
        if (!bindingResult.hasErrors()) {
            UserInfo userInfo = userService.updateProfileImage(avatar.getUsername(), avatar.getB64Image());
            model.addAttribute("userInfo", userInfo);
        }
        return "redirect:profile";
    }

}
