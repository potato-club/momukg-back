package com.momukgback.Service.Impl;

import com.momukgback.Dto.userStatus.UserStatusRequestDto;
import com.momukgback.Dto.userStatus.UserStatusResponseDto;
import com.momukgback.Entity.User;
import com.momukgback.Entity.UserStatus;
import com.momukgback.Repository.UserRepository;
import com.momukgback.Repository.UserStatusRepository;
import com.momukgback.Service.Interface.UserService;
import com.momukgback.Service.Interface.UserStatusService;
import com.momukgback.enums.UserRole;
import com.momukgback.error.ErrorCode;
import com.momukgback.error.exception.NotFoundException;
import com.momukgback.error.exception.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserStatusServicelmpl implements UserStatusService {

    private final UserStatusRepository userStatusRepository;
    private final UserService userService;
    private final UserRepository userRepository;



    @Override
    public void getStatusCreate(UserStatusRequestDto requestDto, HttpServletRequest request) {
        User user = userService.findUserByToken(request);
        if(user.getUserRole() != UserRole.USER) {
            throw new UnAuthorizedException("401 권한 없음", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION);
        }
        UserStatus userStatus = new UserStatus(requestDto, user);
        userStatusRepository.save(userStatus);

    }

    @Override
    public UserStatusResponseDto getStatusById(Long statusId) {
        Optional<UserStatus> statusAuth = userStatusRepository.findById(statusId);
        if(statusAuth.isEmpty()) throw new NotFoundException("게시물 없음", ErrorCode.NOT_FOUND_EXCEPTION);
        UserStatus userStatus = statusAuth.get();
        UserStatusResponseDto dto = new UserStatusResponseDto(userStatus);
        return dto;
    }


    @Override
    public List<UserStatusResponseDto> getAllStatus() {
        List<UserStatus> userStatuses = userStatusRepository.findAll();

        List<UserStatusResponseDto> responseDtosList = new ArrayList<>();
        for (UserStatus userStatus : userStatuses){
            UserStatusResponseDto responseDto = new UserStatusResponseDto(userStatus);
            responseDtosList.add(responseDto);
        }

        return responseDtosList;
    }

    @Override
    public void updateStatus(Long statusId, UserStatusRequestDto updateDto, HttpServletRequest request) {
            UserStatus userStatus = vlidateStatus(statusId, request);
            userStatus.updateStatus(updateDto);
    }

    @Override
    public void deleteStatus(Long statusId, HttpServletRequest request) {
        vlidateStatus(statusId, request);
        userStatusRepository.deleteById(statusId);
    }

    public UserStatus vlidateStatus(Long statusId, HttpServletRequest request){
        Optional<UserStatus> StatusAuth = userStatusRepository.findById(statusId);
        User user = userService.findUserByToken(request);
        if (StatusAuth.isEmpty()) {
            throw new NotFoundException("게시물 없음", ErrorCode.NOT_FOUND_EXCEPTION);
        }
        UserStatus userStatus = StatusAuth.get();
        if (!userStatus.getUser().equals(user)) {
            throw new UnAuthorizedException("401 권한 없음", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION);
        }
        return userStatus;
    }

}