package com.project.pokemon.Controller;

import com.project.pokemon.Service.UserService;
import com.project.pokemon.model.dto.requestDto.RefreshTokenDto;
import com.project.pokemon.model.dto.requestDto.SignInDto;
import com.project.pokemon.model.dto.requestDto.SignupDto;
import com.project.pokemon.model.dto.responseDto.UserLoginRespDto;
import com.project.pokemon.model.dto.responseDto.UserRegisterRespDto;
import com.project.pokemon.model.dto.responseDto.UserTokenRespDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    // 토큰 테스트
    @GetMapping("/user/tokentest")
    public UserTokenRespDto tokenTest(@RequestHeader("Authorization") String authorization) {
        if(!authorization.startsWith("Bearer "))
            return new UserTokenRespDto(false, "토큰 인증 방식이 Bearer 가 아닙니다.");

        String token = authorization.substring(7, authorization.length() - 1);

        return userService.tokenTest(token);
    }


    // 토큰 재발급 받을때 사용
    @PostMapping("/user/refresh")
    public UserLoginRespDto tokenRefresh(@RequestBody RefreshTokenDto Dto) {
        return userService.verifyRefreshToken(Dto.getAccessToken(), Dto.getRefreshToken());
    }


    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {

        return "login";
    }

    // 로그인 요청 처리
    @PostMapping("/user/login")
    public UserLoginRespDto login(@RequestBody SignInDto Dto) throws NoSuchAlgorithmException {

        return  userService.loginUser(Dto);
    }

    // 회원가입 페이지
    @GetMapping("/user/signup")
    public String signup() {

        return "signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public UserRegisterRespDto registerUser(@RequestBody SignupDto Dto) throws NoSuchAlgorithmException {

        return userService.registerUser(Dto);
    }

    // email 중복 확인
    @ResponseBody
    @GetMapping("/user/emailDupCheck/{email}")
    public Boolean emailDupCheck(@PathVariable String email) {

        return userService.checkEmailDuplicate(email);
    }

    // 닉네임 중복 확인
    @ResponseBody
    @GetMapping("/user/nameDupCheck/{nickname}")
    public Boolean nameDupCheck(@PathVariable String nickname) {

        return userService.checkNameDuplicate(nickname);
    }

}