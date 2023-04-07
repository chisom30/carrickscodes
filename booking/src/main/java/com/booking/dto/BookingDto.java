package com.booking.dto;

import lombok.Data;

@Data
public class BookingDto {
    private Long id;
    private String startDate;
    private String endDate;
    private Long custormerId;
}
