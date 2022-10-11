package com.wcback.wcback.controller;

import com.wcback.wcback.service.OAuthService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@NoArgsConstructor
@RequestMapping("/auth")
public class OAuthConroller {
    OAuthService oAuth;

    @Autowired
    public OAuthConroller(OAuthService oAuthService) {
        oAuth = oAuthService;
    }

    @ResponseBody
    @GetMapping("/kakao/callback")
    public void kakaoCallback(@RequestParam String code){
        String token = oAuth.getKakaoAccessToken(code);
        HashMap<String ,Object> userInfo = oAuth.getUserInfo(token);
        System.out.println(userInfo);
    }
}