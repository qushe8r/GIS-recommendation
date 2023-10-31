package com.wanted.demo.domain.userInfo.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoUpdateReqeust {
    private String latitude;
    private String longitude;
    private Boolean lunchService;
}
