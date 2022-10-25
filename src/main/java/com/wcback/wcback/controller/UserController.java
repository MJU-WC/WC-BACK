package com.wcback.wcback.controller;

import com.wcback.wcback.config.JwtProvider;
import com.wcback.wcback.data.dto.User.UserDto;
import com.wcback.wcback.data.entity.User;
import com.wcback.wcback.exception.user.PassWordErrorException;
import com.wcback.wcback.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    // 유저 닉네임 중복 체크
    @GetMapping("/checkEmail/{email}")
    public ResponseEntity<Object> checkEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.checkUser(email));
    }

    // 회원가입
    @PostMapping("/register")
    private ResponseEntity<Object> Register(@RequestBody UserDto.UserRequestDto data) {
        return new ResponseEntity<>(userService.register(data),HttpStatus.CREATED);
    }

    // 로그인
    @GetMapping("/login")
    public ResponseEntity<Object> Login(@RequestBody UserDto.UserRequestDto data) {
        User loginUser = userService.findUserByEmail(data.getEmail());
        if (!Objects.equals(loginUser.getPassword(),data.getPassword())) {
            throw new PassWordErrorException("잘못된 비밀번호입니다.");
        }
        String token = jwtProvider.createToken(loginUser.getEmail());
        userService.updateToken(loginUser.getId(), token);

        UserDto.UserInfoDto userInfoDto = new UserDto.UserInfoDto();
        userInfoDto.setUserName(loginUser.getUserName());
        userInfoDto.setToken(token);
        return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
    }

     //회원정보 수정
    @PatchMapping("/modify")
    private ResponseEntity<Object> Modify(@RequestBody UserDto.UserRequestDto data) {
        return new ResponseEntity<>(userService.modify(data), HttpStatus.OK);
    }

}

