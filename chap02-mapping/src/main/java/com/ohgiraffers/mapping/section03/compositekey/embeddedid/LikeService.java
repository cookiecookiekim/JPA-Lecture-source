package com.ohgiraffers.mapping.section03.compositekey.embeddedid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository repository;

    @Transactional
    public void registLikeInfo(LikeDTO likeDTO) {

        Like like = new Like(
                new LikedCompositkey( // compositkey 인스턴스 생성
                        new LikedMemberNo(likeDTO.getLikedMemberNo())
                       ,new LikedBookNo(likeDTO.getLikedBookNo())
                )
        );
        repository.save(like); // 영속화 요청
    }
}
