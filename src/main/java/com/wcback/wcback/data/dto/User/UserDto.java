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
        private String password;
        private String userName;
        private String profile_image;
        private String address;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfoDto {
        private String id;
        private String userName;
        private String email;
        private String password;
        private String profile_image;
        private String address;
        private String token;
    }
}
