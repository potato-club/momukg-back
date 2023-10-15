package com.momukgback.Dto.Login;

import com.momukgback.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponseDto {
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "유저 역할", example = "USER / MANAGER")
    private UserRole userRole;
}
