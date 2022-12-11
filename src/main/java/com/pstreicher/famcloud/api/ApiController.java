package com.pstreicher.famcloud.api;

import com.pstreicher.famcloud.domain.UserInfo;
import com.pstreicher.famcloud.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ApiController {

    private final UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    UserInfo getProfile() {
        return userService.getUserByUsername("jeki");
    }
}
