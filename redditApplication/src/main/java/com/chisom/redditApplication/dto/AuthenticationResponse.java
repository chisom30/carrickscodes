package com.chisom.redditApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private String username;
    private Long userId;
}
