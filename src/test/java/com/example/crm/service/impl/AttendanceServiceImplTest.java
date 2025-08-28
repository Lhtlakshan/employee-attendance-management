package com.example.crm.service.impl;

import com.example.crm.dto.AttendanceDto;
import com.example.crm.entity.AttendanceEntity;
import com.example.crm.entity.UserEntity;
import com.example.crm.enums.UserRole;
import com.example.crm.repository.AttendanceRepository;
import com.example.crm.repository.UserRepository;
import com.example.crm.service.AttendanceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceImplTest {

    @Mock
    AttendanceRepository attendanceRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ModelMapper modelMapper;
    @InjectMocks
    AttendanceServiceImpl attendanceService; //product repo inject to this

    @Test
    void checkInTest() {
        String email = "john@example.com";
        UserEntity user = new UserEntity(1, "John", email, "lock123", UserRole.EMPLOYEE);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        AttendanceEntity attendanceEntity = new AttendanceEntity(user, LocalDate.now(), LocalTime.now());

        AttendanceDto mockedDto = new AttendanceDto();
        mockedDto.setUserId(user.getUserId());

        // Mock the mapper to return the expected DTO
        Mockito.when(modelMapper.map(Mockito.any(AttendanceEntity.class), Mockito.eq(AttendanceDto.class)))
                .thenReturn(mockedDto);

        Mockito.when(attendanceRepository.existsByUserAndDate(Mockito.any(), Mockito.any()))
                .thenReturn(false);

        Mockito.when(attendanceRepository.save(Mockito.any(AttendanceEntity.class)))
                .thenReturn(attendanceEntity);

        // Act
        AttendanceDto result = attendanceService.checkIn(email);

        // Assert
        Assertions.assertEquals(mockedDto.getUserId(), result.getUserId());
    }

    @Test
    void checkOutTest() {
    }

    @Test
    void getAllAttendanceRecordsTest() {
    }

    @Test
    void getOwnAttendanceRecordsTest() {
    }
}