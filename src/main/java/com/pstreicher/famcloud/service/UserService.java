package com.pstreicher.famcloud.service;

import com.pstreicher.famcloud.domain.UserInfo;
import com.pstreicher.famcloud.dto.UserInfoDTO;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    UserInfo getUserByUsername(String username);
    UserInfoDTO getUserInfoDTO(Authentication auth);
    UserInfo getUserInfo(Authentication auth);
    UserInfo createNewUser(IDToken idToken);
    UserInfo updateProfileImage (String username, String b64Image);
    UserInfo updateUser(UserInfo userInfo);
    List<UserInfoDTO> getAllusers();

}
