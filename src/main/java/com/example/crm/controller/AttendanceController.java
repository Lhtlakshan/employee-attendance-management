package com.example.crm.controller;

import com.example.crm.dto.AttendanceDto;
import com.example.crm.service.AttendanceService;
import com.example.crm.utility.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // Helper to get current authenticated username
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Returns username
    }

    // check in API
    @PostMapping("/check-in")
    public ResponseEntity<ApiResponse<String>> checkIn(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                    "Check in successfully",
                    attendanceService.checkIn(getCurrentUsername())
            ));
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    "Cannot be check in" + ex.getMessage(),null));
        }
    }

    // Check out
    @PutMapping("/check-out")
    public ResponseEntity<ApiResponse<String>> checkOut(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                    "Checked out successfully",
                    attendanceService.checkOut(getCurrentUsername())
            ));
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    "Cannot be check in" + ex.getMessage(),null
            ));
        }
    }

    //get own records
    @GetMapping("/my-logs")
    public ResponseEntity<ApiResponse<List<AttendanceDto>>> getOwnAttendanceRecords(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                    "Checked out successfully",
                    attendanceService.getOwnAttendanceRecords(getCurrentUsername())
            ));
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    "Cannot be check in" + ex.getMessage(),null
            ));
        }
    }

    // get all attendance record
    @GetMapping
    public ResponseEntity<ApiResponse<List<AttendanceDto>>> getAllAttendanceRecords(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                    "All attendance logs",
                    attendanceService.getAllAttendanceRecords()
            ));
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    "Cannot received" + ex.getMessage(),null
            ));
        }
    }

}
