package com.example.crm.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDto {
    private Integer attendanceId;
    private Integer userId;
    private LocalDate date;
    private LocalTime checkInTime;
    private LocalTime checkoutTime;
}
