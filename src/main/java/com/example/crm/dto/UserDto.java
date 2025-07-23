package com.example.crm.dto;


import com.example.crm.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;
}
