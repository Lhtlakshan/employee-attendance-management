package com.example.crm.controller;

import com.example.crm.dto.AuthRequestDto;
import com.example.crm.dto.AuthResponseDto;
import com.example.crm.dto.UserDto;
import com.example.crm.service.impl.JwtService;
import com.example.crm.service.UserService;
import com.example.crm.utility.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // admin cannot be register directly but this only demonstration
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> registerUser(@RequestBody UserDto userDto){
        try{
            UserDto user = userService.registerUser(userDto);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("User successfully created",user));
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(
                    "User not created exception: "+ex.getMessage(),null
            ));
        }
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> authenticateAndGetToken(@RequestBody AuthRequestDto authRequest) throws Exception {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            if (authentication.isAuthenticated()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
                String jwtToken = jwtService.generateToken(userDetails);
                UserDto user = userService.searchByEmail(authRequest.getUsername());

                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("userId" , user.getUserId())
                        .put("role" , user.getUserRole());

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization" , "Bearer "+jwtToken);

                return ResponseEntity.ok(new ApiResponse<>("User logged in" , new AuthResponseDto("Bearer " + jwtToken , user.getUserRole())));

            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        }catch(Exception ex){
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}