package com.momukgback.Controller;

import com.momukgback.Dto.Login.UserProfileResponseDto;
import com.momukgback.Dto.userStatus.UserStatusListDto;
import com.momukgback.Dto.userStatus.UserStatusRequestDto;
import com.momukgback.Dto.userStatus.UserStatusResponseDto;
import com.momukgback.Service.Interface.UserStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/UserStatus")
@Tag(name = "User Status & Custom Settings Controller", description = "사용자 상태 맞춤형 설정 API")
public class UserStatusController {

    private final UserStatusService userStatusService;

    @Operation(summary = "모든 유저 상태 조회 API")
    @GetMapping("/options")
    public ResponseEntity<List<UserStatusListDto>> getAllBoards() {
        List<UserStatusListDto> allUesrStatus = userStatusService.getAllStatus();
        return ResponseEntity.ok(allUesrStatus);
    }

    @Operation(summary = "내 유저 상태 조회 API")
    @GetMapping("/{statusId}")
    public UserStatusResponseDto viewStatus(@PathVariable(name = "statusId") Long statusId,
                                            HttpServletRequest request){
        return userStatusService.viewStatus(statusId, request);
    }


    @Operation(summary = "유저 상태 저장 API")
    @PostMapping
    public ResponseEntity<String> getStatusCreate(@RequestBody UserStatusRequestDto userStatusRequestDto,
                                                  HttpServletRequest request){
        userStatusService.getStatusCreate(userStatusRequestDto, request);
        return ResponseEntity.ok("사용자의 상태가 저장되었습니다.");
    }

    @Operation(summary = "유저 상태 삭제 API")
    @DeleteMapping("/{statusId}")
    public ResponseEntity<String> deleteBoard(@PathVariable(name = "statusId") Long statusId,
                                              HttpServletRequest request){
        userStatusService.deleteStatus(statusId, request);
        return  ResponseEntity.ok("유저 상태가 삭제되었습니다.");
    }




    @Operation(summary = "유저 상태 수정 API")
    @PutMapping("/{statusId}")
    public ResponseEntity<String> updateStatus(@PathVariable(name = "statusId") Long statusId,
                                              @RequestBody UserStatusRequestDto updateDto,
                                              HttpServletRequest request){
        userStatusService.updateStatus(statusId, updateDto, request);
        return ResponseEntity.ok("유저 상태 수정이 완료되었습니다.");
    }





}
