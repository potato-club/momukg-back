package com.momukgback.Controller;

import com.momukgback.Dto.Login.UserLoginRequestDto;
import com.momukgback.Dto.Login.UserLoginResponseDto;
import com.momukgback.Dto.Login.UserSignUpDto;
import com.momukgback.Service.Interface.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "users")
@Tag(name = "Users & Authorization Controller", description = "유저 및 인증 API")
public class UserController {
    private final UserService userService;


    @Operation(summary = "회원가입 API")
    @PostMapping("/signup")
    public ResponseEntity<String> userSignUp(@RequestBody UserSignUpDto requestDto, HttpServletResponse response) throws Exception {
        userService.signUP(requestDto, response);
        return ResponseEntity.ok("회원가입 완료");
    }

    @Operation(summary = "일반 로그인 API")
    @GetMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto, response);
    }

}
