package com.pstreicher.famcloud.domain;


import lombok.Data;

import java.util.Base64;

@Data
public class AvatarFormCommand {

    private String b64Image;
    private String username;
}
