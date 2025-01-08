package com.ohgiraffers.mapping.section03.compositekey.embeddedid;

// section03 - 복합키
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class LikeDTO { // 좋아요 누르는 거 가정

    private int likedMemberNo;
    private int likedBookNo;
}
