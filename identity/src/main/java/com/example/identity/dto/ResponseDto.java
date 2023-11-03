package com.example.identity.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private String username;
    private String token;
    private String role;
}
