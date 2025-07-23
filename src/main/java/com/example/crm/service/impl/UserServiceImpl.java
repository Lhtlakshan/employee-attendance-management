package com.example.crm.service.impl;

import com.example.crm.dto.UserDto;
import com.example.crm.entity.UserEntity;
import com.example.crm.repository.UserRepository;
import com.example.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userDetail = userRepository.findByEmail(username); // Assuming 'email' is used as username

        return userDetail.map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setPassword(null);
        userRepository.save(userEntity);
        return userDto;
    }

    @Override
    public UserDto searchByEmail(String username) {
        UserEntity user = userRepository.findByEmail(username).get();
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public boolean findByEmail(String username) {
        return userRepository.existsByEmail(username);
    }
}
