package com.momukgback.Service.Interface;

import com.momukgback.Dto.Login.UserLoginRequestDto;
import com.momukgback.Dto.Login.UserLoginResponseDto;
import com.momukgback.Dto.Login.UserProfileResponseDto;
import com.momukgback.Dto.Login.UserSignUpDto;
import com.momukgback.Entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    void signUP(UserSignUpDto userSignUpDto, HttpServletResponse response) throws Exception;
    UserLoginResponseDto login(UserLoginRequestDto requestDto, HttpServletResponse response);
    UserProfileResponseDto viewUser(HttpServletRequest request);

    User findUserByToken(HttpServletRequest request);
}
