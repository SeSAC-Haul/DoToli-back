package org.example.dotoli.dto.member;

import lombok.Data;

/**
 * MyPage 정보를 담는 DTO 클래스
 */
@Data
public class MyPageResponseDto {

    private String email;

    private String nickname;

    private int totalTaskCount;

    private int completedTaskCount;

    private int achievementRate;

    public MyPageResponseDto(
            String email, String nickname, int totalTaskCount, int completedTaskCount, int achievementRate
    ) {
        this.email = email;
        this.nickname = nickname;
        this.totalTaskCount = totalTaskCount;
        this.completedTaskCount = completedTaskCount;
        this.achievementRate = achievementRate;
    }

}
