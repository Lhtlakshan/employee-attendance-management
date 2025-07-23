package com.example.crm.service;

import com.example.crm.dto.AttendanceDto;

import java.util.List;

public interface AttendanceService {
    String checkIn(String email);
    String checkOut(String currentUserEmail);
    List<AttendanceDto> getAllAttendanceRecords();
    List<AttendanceDto> getOwnAttendanceRecords(String currentUserEmail);
}
