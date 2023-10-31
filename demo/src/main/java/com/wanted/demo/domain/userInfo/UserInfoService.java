package com.wanted.demo.domain.userInfo;

import com.wanted.demo.domain.user.User;
import com.wanted.demo.domain.user.UserEditor;
import com.wanted.demo.domain.userInfo.dto.requests.UserInfoUpdateReqeust;
import com.wanted.demo.domain.userInfo.dto.responses.UserInfoResponse;
import com.wanted.demo.global.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoService {
    private final AuthUtil authUtil;

    public UserInfoResponse getUserInfoResponse() {
        User user = authUtil.getCurrentUser();

        return UserInfoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .latitude(user.getLatitude())
                .longitude(user.getLongitude())
                .lunchService(user.getLunchService())
                .build();
    }

    @Transactional
    public void editUserInfo(UserInfoUpdateReqeust userInfoUpdateReqeust) {
        User user = authUtil.getCurrentUser();

        UserEditor userEditor = UserEditor.builder()
                .latitude(userInfoUpdateReqeust.getLatitude())
                .longitude(userInfoUpdateReqeust.getLongitude())
                .lunchService(userInfoUpdateReqeust.getLunchService())
                .build();

        user.edit(userEditor);
    }
}
