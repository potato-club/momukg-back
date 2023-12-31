package com.momukgback.Service.Impl;

import com.momukgback.Dto.userStatus.UserStatusListDto;
import com.momukgback.Dto.userStatus.UserStatusRequestDto;
import com.momukgback.Dto.userStatus.UserStatusResponseDto;
import com.momukgback.Entity.User;
import com.momukgback.Entity.UserStatus;
import com.momukgback.JWT.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    /*@Override
    public UserStatusResponseDto getStatusById(Long statusId) {
        Optional<UserStatus> statusAuth = userStatusRepository.findById(statusId);
        if(statusAuth.isEmpty()) throw new NotFoundException("유저 상태 없음", ErrorCode.NOT_FOUND_EXCEPTION);
        UserStatus userStatus = statusAuth.get();
        UserStatusResponseDto dto = new UserStatusResponseDto(userStatus);
        return dto;
    }


    */

    @Override
    public UserStatus getUserStatus(Long id) {
        return userStatusRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 아이디 입니다.", ErrorCode.NOT_FOUND_EXCEPTION));
    }


    @Override
    public UserStatusResponseDto viewStatus(Long id, HttpServletRequest request){
        UserStatus useridStatus = getUserStatus(id);
        UserStatusResponseDto responseDto = new UserStatusResponseDto(useridStatus);
        return responseDto;
    }

    @Override
    public void updateStatus(Long userId, UserStatusRequestDto updateDto, HttpServletRequest request) {
            UserStatus userStatus = vlidateStatus(userId, request);
            userStatus.updateStatus(updateDto);
    }

    @Override
    public List<UserStatusListDto> getAllStatus() {
        List<UserStatus> userStatuses = userStatusRepository.findAll();

        List<UserStatusListDto> responseDtosList = new ArrayList<>();
        for (UserStatus userStatus : userStatuses){
            UserStatusListDto responseDto = new UserStatusListDto(userStatus);
            responseDtosList.add(responseDto);
        }

        return responseDtosList;
    }



    @Override
    public void getStatusCreate(UserStatusRequestDto requestDto, HttpServletRequest request) {
        User user = userService.findUserByToken(request);
        if(user.getUserRole() != UserRole.USER) {
            throw new UnAuthorizedException("401 권한 없음", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION);
        }
        Optional<UserStatus> existingUserStatus = userStatusRepository.findByUserId(user.getId());
        if (existingUserStatus.isPresent()) {
            throw new UnAuthorizedException("403 이미 내용이 존재합니다.", ErrorCode.NOT_ALLOW_WRITE_EXCEPTION);
        }
        UserStatus userStatus = new UserStatus(requestDto, user);
        userStatusRepository.save(userStatus);

    }

    @Override
    public void deleteStatus(Long userId, HttpServletRequest request) {
        vlidateStatus(userId, request);
        userStatusRepository.deleteByUserId(userId);
    }

    public UserStatus vlidateStatus(Long userId, HttpServletRequest request){
        Optional<UserStatus> StatusAuth = userStatusRepository.findByUserId(userId);
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
