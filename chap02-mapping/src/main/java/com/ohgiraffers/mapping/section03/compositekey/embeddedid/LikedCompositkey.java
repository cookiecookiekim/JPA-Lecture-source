package com.ohgiraffers.mapping.section03.compositekey.embeddedid;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;

// section03 - 복합키 가정
@Embeddable // 다른 곳에서 쓸 수 있게 Embeddable로
public class LikedCompositkey {

    @Embedded
    private LikedMemberNo likedMemberNo;

    @Embedded
    private LikedBookNo likedBookNo;

    public LikedCompositkey(){}

    public LikedCompositkey(LikedMemberNo likedMemberNo, LikedBookNo likedBookNo) {
        this.likedMemberNo = likedMemberNo;
        this.likedBookNo = likedBookNo;
    }
}
