package com.wanted.demo.domain.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String latitude; // 위도
    private String longitude; // 경도
    private Boolean lunchService;

    public void edit(UserEditor userEditor) {
        if (userEditor.getLatitude() != null) {
            latitude = userEditor.getLatitude();
        }
        if (userEditor.getLongitude() != null) {
            longitude = userEditor.getLongitude();
        }
        if (userEditor.getLunchService() != null) {
            lunchService = userEditor.getLunchService();
        }
    }
}
