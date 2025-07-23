package com.example.crm.repository;

import com.example.crm.entity.AttendanceEntity;
import com.example.crm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity,Integer> {
    boolean existsByUserAndDate(UserEntity user, Date date);
    AttendanceEntity findByUserAndDate(UserEntity user, Date date);
    List<AttendanceEntity> findByUser(UserEntity user);
}
