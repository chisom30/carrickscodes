package com.ebanking.dto;

import lombok.Data;

@Data
public class UsersDto {
    private Long id;
    private String username;
    private String password;
    private Long accountId;
    private Long roleId;
}
