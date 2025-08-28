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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final UserRepository  userRepository;
    private final AttendanceRepository attendanceRepository;
    private final ModelMapper modelMapper;

    @Override
    public AttendanceDto checkIn(String email) {

        UserEntity user = userRepository.findByEmail(email).get();

        //check relevant user exist
        if(user == null){
            throw new RuntimeException("User not found");
        }

        //check user already checked on today
        if(attendanceRepository.existsByUserAndDate(user, LocalDate.now())) {
            throw new RuntimeException("User already check in");
        }

        AttendanceEntity attendance = new AttendanceEntity(user, LocalDate.now(), LocalTime.now());
        AttendanceEntity attendance1 = attendanceRepository.save(attendance);
        AttendanceDto attendanceDto = modelMapper.map(attendance1, AttendanceDto.class);
        attendanceDto.setUserId(user.getUserId());
        return attendanceDto;
    }

    @Override
    public AttendanceDto checkOut(String email) {

        UserEntity user = userRepository.findByEmail(email).get();

        //check relevant user exist
        if(user == null){
            throw new RuntimeException("User not found");
        }

        AttendanceEntity checkIn = attendanceRepository.findByUserAndDate(user, LocalDate.now());

        //check user already checked on today and checkout time == null
        if(checkIn != null && checkIn.getCheckoutTime() == null) {
            checkIn.setCheckoutTime(LocalTime.now());
            AttendanceEntity attendance1 = attendanceRepository.save(checkIn);
            AttendanceDto attendanceDto = modelMapper.map(attendance1, AttendanceDto.class);
            attendanceDto.setUserId(user.getUserId());
            return attendanceDto;
        }else{
            throw new RuntimeException("User already checked out");
        }

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
