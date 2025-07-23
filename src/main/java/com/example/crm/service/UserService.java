package com.example.crm.service;

import com.example.crm.dto.UserDto;

public interface UserService{
    UserDto registerUser(UserDto userDto);
    UserDto searchByEmail(String username);
    boolean findByEmail(String username);
}
