package com.ohgiraffers.mapping.section03.compositekey.embeddedid;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/* section03은 복합키 관련 설정인데
 인조 식별자가 있으므로, 알고만 넘어가면 될 듯. */
@Entity
@Table(name = "tbl_like")
public class Like {

    @EmbeddedId
    private LikedCompositkey likeInfo; // 클래스 생성

    public Like(){}

    public Like(LikedCompositkey likeInfo) {
        this.likeInfo = likeInfo;
    }
}
