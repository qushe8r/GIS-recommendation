package com.wanted.demo.domain.user.domain;

import com.wanted.demo.store.entity.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

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

    public User(Long id) {
        this.id = id;
    }

    public void write(Review review) {
        if (!this.reviews.contains(review)) {
            this.reviews.add(review);
            review.reviewer(this);
        }
    }
}
