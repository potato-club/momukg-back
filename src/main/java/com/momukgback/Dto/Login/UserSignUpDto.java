package com.momukgback.Dto.Login;

import com.momukgback.Entity.User;
import com.momukgback.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSignUpDto {
    private String nickname;
    private String email;
    private String password;

    public User toEntity(){
        User user = User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .userRole(UserRole.USER)
                .build();
        return user;
    }
}
