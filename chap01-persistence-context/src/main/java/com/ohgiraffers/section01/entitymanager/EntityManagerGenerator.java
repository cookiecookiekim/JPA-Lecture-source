package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// ③ 공장 생성했으니 매니저 생성하는 클래스
public class EntityManagerGenerator {

    // 공장에 대한 매니저를 만드는 메서드
    public static EntityManager getInstance() {
        // EntityManagerFactoryGenerator에서 만든 공장의 인스턴스 생성
        EntityManagerFactory factory =
                EntityManagerFactoryGenerator.getInstance();

        return factory.createEntityManager();
    }

}
