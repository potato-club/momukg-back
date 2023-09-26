package com.momukgback.Entity;

import com.momukgback.Dto.userStatus.UserStatusRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "userStatus")
public class UserStatus extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userStatus_id")
    private Long id;

    @Column(nullable = false)
    private String yesfood;

    @Column(nullable = false) 
    private int money;

    // 사용자화에 들어갈 기본 세팅에 대한 정보 저장 값은 추후 협의 후 추가
    
    // Many-to-One relationship with User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserStatus(UserStatusRequestDto requestDto, User user) {
        this.yesfood = requestDto.getYesfood();
        this.money = requestDto.getMoney();
        this.user = user;
    }


    public void updateStatus(UserStatusRequestDto updateDto){
        this.yesfood = updateDto.getYesfood();
        this.money = updateDto.getMoney();
    }
}
