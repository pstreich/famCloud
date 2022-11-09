//package com.pstreicher.famcloud.authN.model;
//
//
//import com.pstreicher.famcloud.authZ.model.Role;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    private String username;
//
//    private String password;
//
//
//    private Set<Role> roles;
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Long getId() {
//        return id;
//    }
//}
