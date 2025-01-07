package com.ohgiraffers.section02.crud;

import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

// 매니저를 통해 CRUD 해보기
public class EntityManagerCRUD {

    private EntityManager manager;

    public EntityManager getManagerInstance() {
        return manager;
    }

    public Menu findMenuByMenuCode(int menuCode) {

        manager = EntityManagerGenerator.getInstance();

        // 조회 하기 위한 find 메서드
        return manager.find(Menu.class, menuCode);
    }


    public Long saveAndReturnCount(Menu newMenu) {
        // insert 구문 동작
        manager = EntityManagerGenerator.getInstance();

        // DML 구문이므로 transaction 열어주기
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        // 등록(insert)를 위한 persist 메서드
        manager.persist(newMenu);
        // 등록한 엔티티를 반영하라는 메서드 flush
        manager.flush();

        // 너무 길어지니, Long 타입 메서드 만들어서 반환
        // manager 정보로 모두 전달 이유 : insert 이후, select도 해야하므로
        // → 2가지 일
        return getCount(manager);
    }
    private Long getCount(EntityManager manager) {
        return manager.createQuery("SELECT COUNT(*) FROM section02Menu",
                Long.class).getSingleResult();
        // from 절이 테이블 명이 아닌 이유 : DB 자체가 아닌 스냅샷 정보이므로?
        // → DB에 가서 조회하는 게 아님.
    }
}
