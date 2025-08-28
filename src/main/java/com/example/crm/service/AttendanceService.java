package com.example.crm.service;

import com.example.crm.dto.AttendanceDto;

import java.util.List;

public interface AttendanceService {
    AttendanceDto checkIn(String email);
    AttendanceDto checkOut(String currentUserEmail);
    List<AttendanceDto> getAllAttendanceRecords();
    List<AttendanceDto> getOwnAttendanceRecords(String currentUserEmail);
}
