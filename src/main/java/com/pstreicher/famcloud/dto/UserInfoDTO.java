package com.pstreicher.famcloud.dto;

import com.pstreicher.famcloud.domain.HobbyRadar;
import lombok.*;

import javax.persistence.Lob;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String profileImage;
    private HobbyRadar hobbyRadar;
}
