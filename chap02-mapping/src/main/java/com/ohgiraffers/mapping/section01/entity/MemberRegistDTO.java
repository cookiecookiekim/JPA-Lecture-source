package com.ohgiraffers.mapping.section01.entity;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class MemberRegistDTO { // DTO는 화면에서 받아올 데이터 위주로 구성
                            // Entity는 반대 개념.
    private String memberId;
    private String memberPwd;
    private String memberName;
    private String phone;
    private String address;
    private LocalDateTime enrollDate;
    private MemberRole memberRole;
    private String status;
}
