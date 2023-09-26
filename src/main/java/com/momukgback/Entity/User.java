package com.momukgback.Entity;

import com.momukgback.enums.UserRole;
import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.*;

@Getter
@Builder
@Entity
@Setter
@RequiredArgsConstructor
@Table(name = "USERS")
@AllArgsConstructor
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Column(length = 500)
    private String refreshToken;

}
