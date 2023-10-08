package com.momukgback.Dto.Login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponseDto {
    @Schema(description = "응답 코드", example = "200_OK")
    private String responseCode;
}
