package com.ohgiraffers.mapping.section01.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService { // 컨트롤러에서 데이터 받았다고 가정

    @Autowired
    private MemberRepository memberRepository; // 의존성 주입

    @Transactional
    public void registMember(MemberRegistDTO newMember) {
        // DTO 자체를 Entity로 바꾸기 (여기서 Member 클래스 작성하고 옴)

        Member member = new Member(
                newMember.getMemberId()
                ,newMember.getMemberPwd()
                ,newMember.getMemberName()
                ,newMember.getPhone()
                ,newMember.getAddress()
                ,newMember.getEnrollDate()
                ,newMember.getMemberRole()
                ,newMember.getStatus()
        );

        memberRepository.save(member);
    }
}
