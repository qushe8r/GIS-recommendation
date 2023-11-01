package com.wanted.demo.domain.userInfo;

import com.wanted.demo.domain.userInfo.dto.requests.UserInfoUpdateReqeust;
import com.wanted.demo.domain.userInfo.dto.responses.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Slf4j
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping(value = "/info")
    public ResponseEntity<UserInfoResponse> getuserInfo() {
        UserInfoResponse userInfoResponse = userInfoService.getUserInfoResponse();
        return ResponseEntity.ok().body(userInfoResponse);
    }

    @PatchMapping(value = "/update")
    public void updateUserInfo(@RequestBody UserInfoUpdateReqeust userInfoUpdateReqeust) {
        userInfoService.editUserInfo(userInfoUpdateReqeust);
    }
}
