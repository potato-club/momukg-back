package com.momukgback.Service.Interface;

import com.momukgback.Dto.userStatus.UserStatusRequestDto;
import com.momukgback.Dto.userStatus.UserStatusResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserStatusService {
    UserStatusResponseDto getStatusById(Long statusId);
    void getStatusCreate( UserStatusRequestDto requestDto,
                         HttpServletRequest request);

    List<UserStatusResponseDto> getAllStatus();

    void updateStatus(Long statusId, UserStatusRequestDto updateDto,
                     HttpServletRequest request);
    void deleteStatus(Long statusId, HttpServletRequest request);

}
