package com.wcback.wcback.controller;

import com.wcback.wcback.config.JwtProvider;
import com.wcback.wcback.data.dto.User.UserDto;
import com.wcback.wcback.data.entity.User;
import com.wcback.wcback.exception.user.AlreadyExistException;
import com.wcback.wcback.service.OAuthService;
import com.wcback.wcback.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class OAuthConroller {
    private final OAuthService oAuth;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    HttpHeaders header = new HttpHeaders();
    // 카카오 로그인 + 회원가입
    @ResponseBody
    @GetMapping("/kakao/callback")
    public ResponseEntity<Object> kakaoCallback(@RequestParam String code) throws AlreadyExistException {

        String token = oAuth.getKakaoAccessToken(code);
        HashMap<String ,Object> userInfo = oAuth.getUserInfo(token);
        String email = userInfo.get("email").toString();

        // 아직 가입되지 않은 이메일은 먼저 회원가입 처리
        if(!userService.checkUser(email)) {
            UserDto.UserRequestDto userRequestDto = new UserDto.UserRequestDto();
            userRequestDto.setUserName(userInfo.get("userName").toString());
            userRequestDto.setEmail(userInfo.get("email").toString());
            userRequestDto.setProfile_image(userInfo.get("profile_image").toString());
            userService.register(userRequestDto);
        }

        // 로그인 처리
        User loginUser = userService.findUserByEmail(email);
        String JwtToken = jwtProvider.createToken(email);
        userService.updateToken(loginUser.getEmail(), JwtToken);

        UserDto.UserInfoDto userInfoDto = new UserDto.UserInfoDto();
        userInfoDto.setUserName(loginUser.getUserName());
        userInfoDto.setToken(token);
        return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
    };
}