package com.ohgiraffers.section02.crud;

import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

// 매니저를 통해 CRUD 해보기
public class EntityManagerCRUD {

    private EntityManager manager; // 자바 제공

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

        // test에서 insert 시 DB에 반영은 되지만 저장되지 않음.
        // 여기서 commit 이후, insert 시 test run 반영된 것 이후로 저장
        /* ex) 21번 메뉴까지 있었는데 commit 전, test에서 3번 run하면 저장 안 되지만 24까지 반영
            → transaction.commit(); 이후 test에서 run 시, 25에 해당 메뉴 저장 */
        transaction.commit();

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

    public Menu modifyMenuName(int code, String name) { // 메뉴 이름 수정하는 메서드
        // 이름 바꾸기 위해서 해당 메뉴 찾아야 함. (위에 만들어 놓은 findMenuByMenuCode 호출)
        Menu foundMenu = findMenuByMenuCode(code);
        System.out.println("foundMenu = " + foundMenu); // code 넘기고 잘 조회 됐는지 출력

        // 이미 findMenuByMenuCode 여기서 찾아온 매니저가 있는데
        // EntityManagerGenerator.getInstance(); 이렇게 다시 생성하면 찾아온 정보가 날라감.
//        manager = EntityManagerGenerator.getInstance();

        // update도 DML 이므로 transaction 시작
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        foundMenu.setMenuName(name); // name = test에서 전달한 우삼겹백반
        transaction.commit();
        return foundMenu;
    }

    public Long removeAndReturnCount(int code) {
        // 삭제 전, 메뉴 특정
        Menu foundMenu = findMenuByMenuCode(code);

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        // 삭제를 위한 메서드 remove
        manager.remove(foundMenu); // 우리가 찾은 메뉴 타입의 항목을 삭제하겠다.

        transaction.commit();
        return getCount(manager);
    }
}
