package com.example.beshoes.models.request;

import lombok.Data;

@Data
public class AddRoletoUserRequest {
    private String username;
    private String rolename;
}
