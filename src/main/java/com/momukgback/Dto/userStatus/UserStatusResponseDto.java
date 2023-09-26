package com.momukgback.Dto.userStatus;

import com.momukgback.Entity.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserStatusResponseDto {
    private String yesfood;
    private Integer money;

    public UserStatusResponseDto(UserStatus userStatus){
        this.yesfood = userStatus.getYesfood();
        this.money = userStatus.getMoney();
    }

}
