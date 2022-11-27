package com.wcback.wcback.controller;

import com.wcback.wcback.config.JwtProvider;
import com.wcback.wcback.data.dto.User.UserDto;
import com.wcback.wcback.data.entity.User;
import com.wcback.wcback.exception.user.AlreadyExistException;
import com.wcback.wcback.exception.user.PassWordErrorException;
import com.wcback.wcback.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    // 유저 닉네임 중복 체크
    @Transactional(readOnly = true)
    @GetMapping("/checkEmail/{email}")
    public ResponseEntity<Object> checkEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.checkUser(email));
    }

    // 회원가입
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<Object> Register(@RequestBody UserDto.UserRegisterDto data) throws AlreadyExistException {
        data.setPwd(passwordEncoder.encode(data.getPwd()));
        return ResponseEntity.ok().body(userService.register(data));
    }

    // 로그인
    @Transactional(readOnly = true)
    @GetMapping("/login")
    public ResponseEntity<Object> Login(@RequestBody UserDto.UserRegisterDto data) {
        User loginUser = userService.findUserByEmail(data.getEmail());

        if (!passwordEncoder.matches(data.getPwd(), loginUser.getPwd())) {
            throw new PassWordErrorException("잘못된 비밀번호입니다.");
        }

        String token = jwtProvider.createToken(loginUser.getEmail());
        userService.updateToken(loginUser.getEmail(), token);

        UserDto.UserLoginDto userInfoDto = new UserDto.UserLoginDto();
        userInfoDto.setName(loginUser.getName());
        userInfoDto.setToken(token);

        return ResponseEntity.ok().body(userInfoDto);
    }

    //회원정보 수정
    @Transactional
    @PatchMapping("/modify")
    public ResponseEntity<Object> Modify(@RequestBody UserDto.UserRegisterDto data) {
        data.setPwd(passwordEncoder.encode(data.getPwd()));
        return ResponseEntity.ok().body(userService.modify(data));
    }
    @Transactional
    @DeleteMapping("/withdraw")
    public ResponseEntity<Object> Withdraw(@RequestBody UserDto.UserRegisterDto data) {
        userService.deleteUser(data.getEmail());
        return ResponseEntity.ok().body("탈퇴완료");
    }
}

