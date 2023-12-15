package com.momukgback.Service.Interface;

import com.momukgback.Dto.userStatus.UserStatusListDto;
import com.momukgback.Dto.userStatus.UserStatusRequestDto;
import com.momukgback.Dto.userStatus.UserStatusResponseDto;
import com.momukgback.Entity.UserStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserStatusService {
    // UserStatusResponseDto getStatusById(Long statusId);
    List<UserStatusListDto> getAllStatus();
    void updateStatus(Long userId, UserStatusRequestDto updateDto,
                      HttpServletRequest request);
    void getStatusCreate(UserStatusRequestDto requestDto,
                         HttpServletRequest request);

    UserStatus getUserStatus(Long id);

    UserStatusResponseDto viewStatus(Long userId, HttpServletRequest request);

    void deleteStatus(Long userId, HttpServletRequest request);

}
