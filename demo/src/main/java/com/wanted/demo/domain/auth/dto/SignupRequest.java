package com.wanted.demo.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SignupRequest {
    private String username;
    private String password;
    private String latitude;
    private String longitude;
    private Boolean lunchService;
}
