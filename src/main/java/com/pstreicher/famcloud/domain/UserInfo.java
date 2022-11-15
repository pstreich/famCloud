package com.pstreicher.famcloud.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String username;


}
