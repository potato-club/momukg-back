package com.momukgback.Controller;

import com.momukgback.Dto.Login.UserLoginRequestDto;
import com.momukgback.Dto.Login.UserLoginResponseDto;
import com.momukgback.Dto.Login.UserProfileResponseDto;
import com.momukgback.Dto.Login.UserSignUpDto;
import com.momukgback.Service.Interface.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "users")
@Tag(name = "Users & Authorization Controller", description = "유저 및 인증 API")
@Slf4j
public class UserController {
    private final UserService userService;


    @Operation(summary = "회원가입 API")
    @PostMapping("/signup")
    public ResponseEntity<String> userSignUp(@RequestBody UserSignUpDto requestDto, HttpServletResponse response) throws Exception {
        log.info("회원가입 정보 컨트롤러에서 확인", requestDto.getEmail());
        userService.signUP(requestDto, response);
        log.info("회원가입 완료");
        return ResponseEntity.ok("회원가입 완료");
    }

    @Operation(summary = "일반 로그인 API")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto, HttpServletResponse response) {
        log.info("로그인 정보 컨트롤러에서 확인", requestDto.getEmail());
        return userService.login(requestDto, response);
    }
    
    @Operation(summary = "내 정보 확인 API")
    @GetMapping("")
    public UserProfileResponseDto veiwUser(HttpServletRequest request){
        return userService.viewUser(request);
    }

}
