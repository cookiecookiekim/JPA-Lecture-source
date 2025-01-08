package com.ohgiraffers.mapping.section01.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/* comment. JPA 사용 시, @Repository 어노테이션 사용
     (@Mapper DB Access 계층이 마이바티스 전용) */

@Repository
public class MemberRepository {

    /* Entity 매니저를 주입 받아 영속성 컨텍스트가
    *   우리가 만든 Member를 관리할 수 있도록 한다. */
    @PersistenceContext // 나중에 사용 안 하니까 굳이 알 필요 없음.
    private EntityManager manager;

    public void save(Member member) {

        manager.persist(member); // insert 하겠다.
    }
}
