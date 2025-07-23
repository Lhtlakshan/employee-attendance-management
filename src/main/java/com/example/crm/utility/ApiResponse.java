package com.example.crm.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ApiResponse<T> {
    private String message;
    private T data;
}
