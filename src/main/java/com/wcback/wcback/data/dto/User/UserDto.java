package com.wcback.wcback.data.dto.User;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserRequestDto {
        private String email;
        private String userName;
        private String password;
        private String profile_image;
        private Long lat;
        private Long lon;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfoDto {
        private String email;
        private String userName;
        private String password;
        private String profile_image;
        private Long lat;
        private Long lon;
        private String token;
    }

}
