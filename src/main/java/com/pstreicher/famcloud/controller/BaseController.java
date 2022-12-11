package com.pstreicher.famcloud.controller;

import com.pstreicher.famcloud.commands.AvatarFormCommand;
import com.pstreicher.famcloud.dto.HobbyRadarDTO;
import com.pstreicher.famcloud.domain.*;
import com.pstreicher.famcloud.dto.UserInfoDTO;
import com.pstreicher.famcloud.service.HobbyRadarService;
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
import java.util.List;

@Controller
@Slf4j
public class BaseController {

    private final UserService userService;
    private final HobbyRadarService hobbyRadarService;

    public BaseController(UserService userService, HobbyRadarService hobbyRadarService) {
        this.userService = userService;
        this.hobbyRadarService = hobbyRadarService;
    }

    @RequestMapping(value = {"/logout"})
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:index";
    }

    @RequestMapping({"", "/", "/index"})
    public String index(Model model) {
        boolean isAuthZ = AuthUtil.isAuthorized();
        model.addAttribute("isAuthZ", isAuthZ);
        return "index";
    }

    @RequestMapping( "/profile")
    public String profile(Authentication auth, Model model) {
        boolean isAuthZ = AuthUtil.isAuthorized();
        model.addAttribute("isAuthZ", isAuthZ);
        UserInfoDTO userInfo = userService.getUserInfoDTO(auth);
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("avatar", new AvatarFormCommand());
        model.addAttribute("hobbyRadar", new HobbyRadarDTO());
        model.addAttribute("hobbies", HobbyCategories.values());
        return "profile";
    }

    @RequestMapping( "/family")
    public String family(Model model) {
        boolean isAuthZ = AuthUtil.isAuthorized();
        model.addAttribute("isAuthZ", isAuthZ);
        List<UserInfoDTO> userInfoList = userService.getAllusers();
        model.addAttribute("userInfoList", userInfoList);
        return "family";
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

    @PostMapping( "/radar-store")
    public String storeRadar(@ModelAttribute HobbyRadarDTO hobbyRadar, BindingResult bindingResult, Authentication auth) {
        if (!bindingResult.hasErrors()) {
            UserInfo userInfo = userService.getUserInfo(auth);
            HobbyRadar newHobbyRadar = hobbyRadarService.addHobbyRadar(hobbyRadar, userInfo);
            userInfo.setHobbyRadar(newHobbyRadar);
            userService.updateUser(userInfo);
        }
        return "redirect:profile";
    }

    @RequestMapping(value = {"/test"})
    public String test() throws ServletException {
        return "test";
    }

}
