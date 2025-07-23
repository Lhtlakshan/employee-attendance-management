package com.example.crm.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDto {
    private Integer attendanceId;
    private Integer userId;
    private Date date;
    private LocalTime checkInTime;
    private LocalTime checkoutTime;
}
