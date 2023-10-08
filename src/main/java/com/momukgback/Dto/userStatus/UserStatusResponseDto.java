package com.momukgback.Dto.userStatus;

import com.momukgback.Entity.User;
import com.momukgback.Entity.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserStatusResponseDto {

    @Schema(description = "유저 닉네임")
    private String nickname;
    @Schema(description = "유저 욥션 1")
    private String userOption1;
    @Schema(description = "유저 옵션 2")
    private String userOption2;
    @Schema(description = "유저 옵션 3")
    private String userOption3;

    public UserStatusResponseDto(UserStatus userStatus){
        this.nickname = userStatus.getUser().getNickname();
        this.userOption1 = userStatus.getUserOption1();
        this.userOption2 = userStatus.getUserOption2();
        this.userOption3 = userStatus.getUserOption3();
    }

}
