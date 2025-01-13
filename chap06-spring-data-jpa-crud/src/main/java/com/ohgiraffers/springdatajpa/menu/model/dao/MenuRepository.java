package com.ohgiraffers.springdatajpa.menu.model.dao;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/* comment. JpaRepository란?
*   EntityManager와 EntityManagerFactory, EntityTransaction을
*   구현한 클래스이다. 따라서 이제 우리는 미리 구현된 클래스를 상속받아
*   더이상 매니저를 명시적으로 호출할 필요가 없다.
*   JpaRepository<사용할 엔티티 , 해당 엔티티 식별자 타입> */

// Repository를 인터페이스로 만들 시, JpaRepository를 상속 받음
@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer> {
    // 제네릭으로 타입 지정해야하고 Menu Entity를 특정할 수 있는 게
    // int 타입의 menuCode가 특정돼야 하므로 Integer!

    // 정렬하지 않은 쿼리 메서드
//    List<Menu> findByMenuPriceGreaterThan(int menuPrice);
    List<Menu> findByMenuPriceGreaterThanOrderByMenuPrice(int menuPrice);

}
