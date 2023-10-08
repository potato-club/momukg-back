package com.momukgback.Service.Impl;

import com.momukgback.Dto.Login.UserLoginRequestDto;
import com.momukgback.Dto.Login.UserLoginResponseDto;
import com.momukgback.JWT.JwtTokenProvider;
import com.momukgback.enums.UserRole;
import com.momukgback.error.ErrorCode;
import com.momukgback.error.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.momukgback.Dto.Login.UserSignUpDto;
import com.momukgback.Entity.User;
import com.momukgback.Repository.UserRepository;
import com.momukgback.Service.Interface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void signUP(UserSignUpDto requestDto, HttpServletResponse response) {
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new UnAuthorizedException("회원가입 실패", ErrorCode.ACCESS_DENIED_EXCEPTION);
        }
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User user = requestDto.toEntity();
        log.info("회원가입 정보 서비스쪽에서 확인");
        userRepository.save(user);
    }

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto requestDto, HttpServletResponse response) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new UnAuthorizedException("401", ErrorCode.ACCESS_DENIED_EXCEPTION);
        }
        String refreshToken = jwtTokenProvider.createRefreshToken(requestDto.getEmail(), user.getUserRole());
        setJwtTokenInHeader(requestDto.getEmail(), response, refreshToken);

        // RefreshToken을 User 엔티티에 저장하고 데이터베이스에 반영
        user.RefreshToken(refreshToken);
        userRepository.save(user);
        return UserLoginResponseDto.builder()
                .responseCode("200")
                .build();
    }

    public void setJwtTokenInHeader(String email, HttpServletResponse response, String refreshToken) {
        UserRole userRole = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."))
                .getUserRole();

        String accessToken = jwtTokenProvider.createAccessToken(email, userRole);

        jwtTokenProvider.setHeaderAccessToken(response, accessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, refreshToken);
    }

    @Override
    public User findUserByToken(HttpServletRequest request) {
        String email = jwtTokenProvider.getUserEmail(jwtTokenProvider.resolveAccessToken(request));
        User user = userRepository.findByEmail(email).orElseThrow();
        return user;
    }

}
