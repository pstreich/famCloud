package com.pstreicher.famcloud.commands;


import lombok.Data;

@Data
public class AvatarFormCommand {

    private String b64Image;
    private String username;
}
