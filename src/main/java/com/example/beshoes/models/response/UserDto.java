package com.example.beshoes.models.response;

import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String username;
    private String password;
}
