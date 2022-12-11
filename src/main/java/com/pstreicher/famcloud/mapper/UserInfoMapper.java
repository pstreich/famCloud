package com.pstreicher.famcloud.mapper;

import com.pstreicher.famcloud.domain.UserInfo;
import com.pstreicher.famcloud.dto.UserInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Base64;

@Mapper
public interface UserInfoMapper {

    UserInfoMapper MAPPER = Mappers.getMapper(UserInfoMapper.class);

    @Mapping(target = "profileImage", expression = "java(mapProfileImage(userInfo.getProfileImage()))")
    @Mapping(source = "hobbyRadar", target = "hobbyRadar")
    UserInfoDTO userInfoToUserInfoDTO(UserInfo userInfo);

    default String mapProfileImage(byte[] profileImage) {
        if (profileImage == null)
            return null;
        return Base64.getEncoder().encodeToString(profileImage);
    }
}
