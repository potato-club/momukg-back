package com.momukgback.Entity;

import com.momukgback.Dto.userStatus.UserStatusRequestDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "userStatus")
public class UserStatus extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userStatus_id")
    private Long id;

    @Column(nullable = false)
    private String userOption1;

    @Column(nullable = false)
    private String userOption2;

    @Column(nullable = false)
    private String userOption3;


    // 사용자화에 들어갈 기본 세팅에 대한 정보 저장 값은 추후 협의 후 추가

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserStatus(UserStatusRequestDto requestDto, User user) {
        this.userOption1 = requestDto.getUserOption1();
        this.userOption2 = requestDto.getUserOption2();
        this.userOption3 = requestDto.getUserOption3();
        this.user = user;
    }


    public void updateStatus(UserStatusRequestDto updateDto){
        this.userOption1 = updateDto.getUserOption1();
        this.userOption2 = updateDto.getUserOption2();
        this.userOption3 = updateDto.getUserOption3();

    }
}
