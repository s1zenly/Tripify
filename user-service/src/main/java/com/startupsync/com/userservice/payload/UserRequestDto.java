package com.startupsync.com.userservice.payload;

import lombok.Data;

import java.util.Date;

@Data
public class UserRequestDto {
    private int id;

    private String name;

    private String userName;

    private String password;

    private String email;

    private String designation;

    private String bio;

    private String profileImageUrl;

    private String location;

    private String github;

    private String linkedin;

    private Date createdAt;

    private Date updatedAt;
}
