package com.booking.dto;

import lombok.Data;

@Data
public class CustormerDto {
    private Long id;
    private String name;
    private String email;
    private String address;
    private Long userId;
}
