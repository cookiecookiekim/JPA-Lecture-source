package com.ohgiraffers.mapping.section01.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.Stream;

// 25-01-08, 1교시 쿼리문 매핑하여 테스트
@SpringBootTest
public class EntityMappingTest {

    @Autowired
    private MemberService memberService; // 필드 주입

    /* title. Entity Mapping 관련 어노테이션 */

    /* comment. JPA의 핵심은 엔티티와 테이블을 매핑하는 것.
    *   → Entity 클래스를 DB 테이블화 시키는 것.
    *   1. 객체와 테이블 매핑 (@Entity, @Table)
    *   2. 기본키(pk 식별자) 매핑 (@Id)
    *   3. 필드와 테이블 컬럼 매핑(@Colum)
    *   4. 연관관계 매핑(@ManyToOne, @OneToMany, @OneToOne, @JoinColum 등등) */

    public static Stream<Arguments> getMember () {
        return Stream.of(
                Arguments.of(
                        1
                        ,"user01"
                        ,"pass01"
                        ,"너구리"
                        ,"010-5518-2290"
                        ,"서울시 서대문구"
                        ,LocalDateTime.now()
                        ,"ROLE_MEMBER"
                        ,"Y"
                ),
                Arguments.of(
                        2
                        ,"user02"
                        ,"pass02"
                        ,"코알라"
                        ,"010-3318-4490"
                        ,"서울시 중구"
                        ,LocalDateTime.now()
                        ,"ROLE_ADMIN"
                        ,"Y"
                )
        );
    }

    @ParameterizedTest
    @DisplayName("테이블 DDL 테스트")
    @MethodSource("getMember")
    void testCreateTable(int memberNo, String memberId, String memberPwd,
                         String memberName, String phone, String address
                        ,LocalDateTime enrollDate, MemberRole memberRole,
                         String status){        // MemberRole은 enum으로 해보기

        MemberRegistDTO newMember = new MemberRegistDTO(
            memberId,memberPwd,memberName
                ,phone,address,enrollDate
                ,memberRole,status
        );
        // 메서드 검증시 사용하는 메서드, 해당 메서드가 Throw 예외를 발생시키는지 확인
        Assertions.assertDoesNotThrow(
                () -> memberService.registMember(newMember)
        ); // run 돌리고 확인해 보면 실제로 테이블이 생성돼 있음!
    }

}
