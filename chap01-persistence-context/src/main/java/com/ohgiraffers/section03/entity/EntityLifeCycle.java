package com.ohgiraffers.section03.entity;

import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;

public class EntityLifeCycle {

    private EntityManager manager; // 여기서들 매니저들 활동할 수 있게 필드로 선언

    public EntityManager getManagerInstance() {
        return manager;
    }

    public Menu findMenuByMenuCode(int menuCode) {

        manager = EntityManagerGenerator.getInstance(); // 매니저 생성

        // 자바 제공 select인 find 메서드
        return manager.find(Menu.class, menuCode); // menuCode를 기준으로 찾아달라
    }
}
