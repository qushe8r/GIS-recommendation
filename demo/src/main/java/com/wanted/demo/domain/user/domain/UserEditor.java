package com.wanted.demo.domain.user.domain;

import lombok.Getter;

@Getter
public class UserEditor {
    private String latitude;
    private String longitude;
    private Boolean lunchService;

    public UserEditor(String latitude, String longitude, Boolean lunchService) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.lunchService = lunchService;
    }

    public static UserEditorBuilder builder() {
        return new UserEditorBuilder();
    }

    public static class UserEditorBuilder {
        private String latitude;
        private String longitude;
        private Boolean lunchService;

        public UserEditorBuilder latitude(final String latitude) {
            this.latitude = latitude;
            return this;
        }

        public UserEditorBuilder longitude(final String longitude) {
            this.longitude = longitude;
            return this;
        }

        public UserEditorBuilder lunchService(final Boolean lunchService) {
            this.lunchService = lunchService;
            return this;
        }

        public UserEditor build() {
            return new UserEditor(latitude, longitude, lunchService);
        }
    }
}
