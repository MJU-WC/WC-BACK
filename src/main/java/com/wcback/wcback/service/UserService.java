package com.wcback.wcback.service;

import com.wcback.wcback.data.dto.User.UserDto;
import com.wcback.wcback.data.entity.User;
import com.wcback.wcback.data.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 이메일 중복 체크
    @Transactional(readOnly = true)
    public boolean checkUser(String email) {
        return userRepository.existsByEmail(email);
    }

    // 이메일로 유저 찾기
    @Transactional(readOnly = true)
    public User findUserByEmail(String email) throws NoSuchElementException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("가입되지 않은 이메일입니다."));
    }

    //ID로 유저 정보 조회
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 아이디입니다."));
    }

    // 회원가입
    @Transactional
    public User register(UserDto.UserRequestDto userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        return userRepository.save(user);
    }

    // DB에 존재하는 토큰 바꾸기
    @Transactional
    public void updateToken(Long id, String token) {
        User user = findUserById(id);
        user.setAccessToken(token);
        userRepository.save(user);
    }

    // 회원정보 수정
    @Transactional
    public User modify(UserDto.UserRequestDto userRequest) {
        User originUser = userRepository.findByEmail(userRequest.getEmail()).get();
        BeanUtils.copyProperties(userRequest, originUser);
        return userRepository.save(originUser);
    }

}
