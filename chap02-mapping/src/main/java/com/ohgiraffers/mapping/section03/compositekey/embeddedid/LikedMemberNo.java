package com.ohgiraffers.mapping.section03.compositekey.embeddedid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LikedMemberNo { // 여기서 컬럼 지정

    @Column(name = "liked_member_no")
    private int likedMemberNo;

    public LikedMemberNo () {}

    public LikedMemberNo(int likedMemberNo) {
        this.likedMemberNo = likedMemberNo;
    }
}
