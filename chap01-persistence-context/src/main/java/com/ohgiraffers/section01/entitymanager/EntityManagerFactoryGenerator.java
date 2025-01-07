package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// ② EntityManagerFactory 공장 생성할 수 있는 클래스 생성
public class EntityManagerFactoryGenerator {

    // 공장 만들려면, db 관련 설정을 해줘야함
    // → resources - META-INF - persistence.xml
    private static EntityManagerFactory factory // static으로 생성하여, run과 동시에 훑게 만듦.
            // persistence.xml을 바탕으로 공장을 만들어줌.
            = Persistence.createEntityManagerFactory("jpatest"); // unit-name 전달.

    // 싱글톤이므로 외부에서 인스턴트 생성하지 못하게 private으로 접근 제한.
    private EntityManagerFactoryGenerator() {}

    // static 인스턴스를 리턴해주는 메서드 생성
    public static EntityManagerFactory getInstance() {return factory;}

}
