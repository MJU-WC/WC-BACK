package com.wcback.wcback.controller;

import com.wcback.wcback.config.JwtProvider;
import com.wcback.wcback.data.dto.User.UserDto;
import com.wcback.wcback.data.entity.User;
import com.wcback.wcback.exception.user.AlreadyExistException;
import com.wcback.wcback.service.OAuthService;
import com.wcback.wcback.service.UserService;
import lombok.AllArgsConstructor;
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

    // 카카오 로그인 + 회원가입
    @ResponseBody
    @GetMapping("/kakao/callback")
    public ResponseEntity<Object> kakaoCallback(@RequestParam String code) throws AlreadyExistException {

        String token = oAuth.getKakaoAccessToken(code);
        HashMap<String ,Object> userInfo = oAuth.getUserInfo(token);
        String email = userInfo.get("email").toString();

        // 아직 가입되지 않은 이메일은 먼저 회원가입 처리
        if(!userService.checkUser(email)) {
            UserDto.UserRegisterDto userRequestDto = new UserDto.UserRegisterDto();
            userRequestDto.setName(userInfo.get("userName").toString());
            userRequestDto.setEmail(userInfo.get("email").toString());
            userRequestDto.setProfile_image(userInfo.get("profile_image").toString());
            userRequestDto.setIskakao(true);
            userService.register(userRequestDto);
        }

        // 로그인 처리
        User loginUser = userService.findUserByEmail(email);
        String JwtToken = jwtProvider.createToken(email);
        userService.updateToken(loginUser.getEmail(), JwtToken);

        UserDto.UserLoginDto userInfoDto = new UserDto.UserLoginDto();
        userInfoDto.setName(loginUser.getName());
        userInfoDto.setToken(token);
        return ResponseEntity.ok().body(userInfoDto);
    };
}