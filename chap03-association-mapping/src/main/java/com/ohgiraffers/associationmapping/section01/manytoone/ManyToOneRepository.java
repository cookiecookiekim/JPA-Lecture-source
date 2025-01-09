package com.ohgiraffers.associationmapping.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ManyToOneRepository {

    @PersistenceContext
    private EntityManager manager;

    public Menu find(int menuCode) {

        return manager.find(Menu.class, menuCode);
    }

    /* comment. JPQL이란? (조회 할 때만 사용)
    *   - 객체 지향 쿼리 언어중 하나이다.
    *   - 객체 지향 모델에 맞게 작성된 쿼리를 통해 엔티티를
    *       대상으로 검색을 진행하며, 세밀한 조작을 할 수 있다.
    *   - 단 테이블에서 조회하는 것이 아닌, 엔티티를
    *   조회하는 것이기 때문에 from 절에는 테이블명이 아닌, 엔티티명으로 */
    // 카테고리 이름만 빼오기
    public String findCategoryName(int menuCode) {
        // 쿼리문 저장을 위한 변수명 설정
        String jpql = "SELECT c.categoryName " + // 문자열 합치기 때문에 한 칸 띄기
                "FROM menu_and_category m " +
                "JOIN m.category c " +
                "WHERE m.menuCode = :menuCode"; // : 는 변수명이 들어가도록
        return manager.createQuery(jpql, String.class)
                .setParameter("menuCode", menuCode) // :menuCode 설정
                .getSingleResult(); // 쿼리문(결과)이 하나이므로 설정
    } // 단점, 가독성이 좋지 못하다 → 이거 극복한 기술 존재

    public void regist(Menu menu) {
        manager.persist(menu);
    }
}
