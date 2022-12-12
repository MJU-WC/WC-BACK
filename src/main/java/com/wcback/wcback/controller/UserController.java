package com.wcback.wcback.controller;

import com.wcback.wcback.config.JwtProvider;
import com.wcback.wcback.data.dto.User.UserDto;
import com.wcback.wcback.data.entity.User;
import com.wcback.wcback.exception.user.AlreadyExistException;
import com.wcback.wcback.exception.user.PassWordErrorException;
import com.wcback.wcback.service.OAuthService;
import com.wcback.wcback.service.UserService;
import lombok.AllArgsConstructor;
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
    private OAuthService oAuthService;

    // 유저 닉네임 중복 체크
    @Transactional(readOnly = true)
    @GetMapping("/checkEmail/{email}")
    public ResponseEntity<Object> checkEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.checkUser(email));
    }

    // 회원가입
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<Object> Register(@RequestBody UserDto.UserRegisterDto data) {
        try {
            if (userService.checkUser(data.getEmail())) {
                throw new AlreadyExistException("이미 존재하는 회원입니다.");
            }
            data.setPwd(passwordEncoder.encode(data.getPwd()));
            data.setToken(jwtProvider.createToken(data.getEmail()));
            return ResponseEntity.ok().body(userService.register(data));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 로그인
    @Transactional(readOnly = true)
    @PostMapping("/login")
    public ResponseEntity<Object> Login(@RequestBody UserDto.UserRegisterDto data) {
        try {
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
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //회원정보 수정
    @Transactional
    @PatchMapping("/modify")
    public ResponseEntity<Object> Modify(@RequestBody UserDto.UserRegisterDto data) {
        data.setPwd(passwordEncoder.encode(data.getPwd()));
        return ResponseEntity.ok().body(userService.modify(data));
    }

    // 회원탈퇴
    @Transactional
    @DeleteMapping("/withdraw")
    public ResponseEntity<Object> Withdraw(@RequestBody String token) {
        String email = getEmailByJwtToken(token);
        userService.deleteUser(email);
        return ResponseEntity.ok().body("탈퇴완료");
    }

    // 토큰으로 회원정보 가져오기
    @Transactional
    @PostMapping("/getUser")
    public ResponseEntity<Object> getUser(@RequestBody String token) {
        User user = userService.findUserByEmail(getEmailByJwtToken(token));
        return ResponseEntity.ok().body(user);
    }
    
    // 카카오토큰으로 회원정보 가져오기
    @Transactional
    @PostMapping("/getUserByKakaoToken")
    public ResponseEntity<Object> getUserByKakaoToken(@RequestBody String token) {
        return ResponseEntity.ok().body(userService.findUserByEmail(getEmailByKakaoToken(token)));
    }

    // 카카오로그인 시 주소 입력
    @Transactional
    @PostMapping("/getKakaoAddress")
    public ResponseEntity<Object> getKakaoAddress(@RequestBody UserDto.UserRegisterDto userRegisterDto) {
        String email = getEmailByKakaoToken(userRegisterDto.getToken());
        String address = userRegisterDto.getAddress();
        userService.getKakaoAddress(email,address);
        return ResponseEntity.ok().body("주소 입력 완료");
    }
    public String getEmailByJwtToken(String token) {
        System.out.println(token);
        return jwtProvider.getPayload(token);
    }

    public String getEmailByKakaoToken(String token) {
        return oAuthService.getUserInfo(token).get("email").toString();
    }

}

