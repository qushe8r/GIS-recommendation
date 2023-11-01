package com.wanted.demo.domain.userInfo.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String username;
    private String password;
    private String latitude;
    private String longitude;
    private Boolean lunchService;
}
