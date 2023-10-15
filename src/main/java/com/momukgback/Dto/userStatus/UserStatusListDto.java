package com.momukgback.Dto.userStatus;

import com.momukgback.Entity.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatusListDto {
    @Schema(description = "유저 상태 저장 id")
    private Long id;
    @Schema(description = "유저 닉네임")
    private String nickname;
    @Schema(description = "유저 욥션 1")
    private String userOption1;
    @Schema(description = "유저 옵션 2")
    private String userOption2;
    @Schema(description = "유저 옵션 3")
    private String userOption3;

    public UserStatusListDto(UserStatus userStatus) {
        this.id = userStatus.getId();
        this.nickname = userStatus.getUser().getNickname();
        this.userOption1 = userStatus.getUserOption1();
        this.userOption2 = userStatus.getUserOption2();
        this.userOption3 = userStatus.getUserOption3();
    }
}
