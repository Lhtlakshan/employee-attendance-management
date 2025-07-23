package com.example.crm.service.impl;

import com.example.crm.dto.AttendanceDto;
import com.example.crm.entity.AttendanceEntity;
import com.example.crm.entity.UserEntity;
import com.example.crm.repository.AttendanceRepository;
import com.example.crm.repository.UserRepository;
import com.example.crm.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final UserRepository  userRepository;
    private final AttendanceRepository attendanceRepository;
    private final ModelMapper modelMapper;

    @Override
    public String checkIn(String email) {

        UserEntity user = userRepository.findByEmail(email).get();

        //check relevant user exist
        if(user == null){
            throw new RuntimeException("User not found");
        }

        //check user already checked on today
        if(attendanceRepository.existsByUserAndDate(user, new Date())) {
            throw new RuntimeException("User already check in");
        }

        AttendanceEntity attendance = new AttendanceEntity(user, new Date(), LocalTime.now());
        attendanceRepository.save(attendance);
        return "User checked in successfully";
    }

    @Override
    public String checkOut(String email) {

        UserEntity user = userRepository.findByEmail(email).get();

        //check relevant user exist
        if(user == null){
            throw new RuntimeException("User not found");
        }

        AttendanceEntity checkIn = attendanceRepository.findByUserAndDate(user, new Date());

        //check user already checked on today and checkout time == null
        if(checkIn != null && checkIn.getCheckoutTime() == null) {
            checkIn.setCheckoutTime(LocalTime.now());
            attendanceRepository.save(checkIn);
        }else{
            throw new RuntimeException("User already checked out");
        }

        return "User checked out successfully";
    }

    @Override
    public List<AttendanceDto> getAllAttendanceRecords() {

        List<AttendanceEntity> attendanceRecordsList = attendanceRepository.findAll();
        List<AttendanceDto> attendanceDtos = new ArrayList<>();

        attendanceRecordsList.forEach(attendanceEntity -> {
            AttendanceDto attendanceDto = modelMapper.map(attendanceEntity , AttendanceDto.class);
            attendanceDto.setUserId(attendanceEntity.getUser().getUserId());
            attendanceDtos.add(attendanceDto);
        });

        return attendanceDtos;
    }

    @Override
    public List<AttendanceDto> getOwnAttendanceRecords(String email) {
        UserEntity user = userRepository.findByEmail(email).get();

        //check if relevant user not exist
        if(user == null){
            throw new RuntimeException("User not found");
        }
        List<AttendanceEntity> attendanceRecordsList = attendanceRepository.findByUser(user);
        List<AttendanceDto> attendanceDtos = new ArrayList<>();

        attendanceRecordsList.forEach(attendanceEntity -> {
            AttendanceDto attendanceDto = modelMapper.map(attendanceEntity , AttendanceDto.class);
            attendanceDto.setUserId(attendanceEntity.getUser().getUserId());
            attendanceDtos.add(attendanceDto);
        });

        return attendanceDtos;
    }
}
