package com.ohgiraffers.associationmapping.section03.bidirection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
public class BiTest {

    @Autowired
    private BiService service;

    /* comment. 데이터베이스의 테이블은 외래키 하나로
     *   양방햔 조회가 가능하다 (JOIN)
     *   하지만, 객체는 서로 다른 두 단방향 참조를
     *   합쳐서 양 방향이라고 한다.
     *   따라서 두 개의 연관관계 중 주인을 설정(mapped by)
     *   즉, 주인을 설정하는 기준은 외래키를 관리하는(가지고 있는)
     *   엔티티가 주인이 되어야 한다. 따라서 사용 테이블로 따지면,
     *   Menu가 categoryCode를 가지기 때문에 주인이 된다. */

    @DisplayName("양방향 연관관계 매핑 조회")
    @Test
    void ownerFindTest() {
        int menuCode = 10;

        /* comment. Menu 엔티티가 주인인 관계에서 조회 시
         *   처음 조회부터 바로 조인한 결과를 가져오게 된다. */
        Menu foundMenu = service.findMenu(menuCode);
        System.out.println("foundMenu = " + foundMenu);
        // oneToMany쪽 ToString 주석처리 해야 Lazy 에러 안 남.
        Assertions.assertEquals(menuCode, foundMenu.getMenuCode());
    }

    @DisplayName("양방향 연관관계 매핑 조회(주인이 아닌 엔티티로 조회)")
    @Test
    void slaveFindTest() {
        int categoryCode = 10;
        // 주인이 아닌 개체 조회 시 해당 엔티티를 먼저 조회 후,
        // 필요 시 연관된 엔티티 조회하는 쿼리가 실행됨 (쿼리문 두 번 동작)
        Category foundCategory = service.findCategory(categoryCode);

        Assertions.assertEquals(categoryCode, foundCategory.getCategoryCode());
    }

    private static Stream<Arguments> getMenuInfo() {
        return Stream.of(
                Arguments.of(111, "스테이크", 9800, "Y")
        );
    }

    @DisplayName("양방향 연관관계 주인 객체를 이용한 삽입 테스트")
    @ParameterizedTest
    @MethodSource("getMenuInfo") // 주인 입장에서 진행
    void ownerInsert(int menuCode, String menuName,
                     int menuPrice, String orderableStatus){
        // 메뉴에 집어 넣을 Category 객체 조회
        Category category = service.findCategory(4);
        Menu newMenu = new Menu(menuCode,menuName,menuPrice,
                            category, orderableStatus);
        Assertions.assertDoesNotThrow(
                () -> service.registMenu(newMenu)
        );
    }

    private static Stream<Arguments> getCategoryInfo() {
        return Stream.of(
                Arguments.of(112, "양방향테스트", null)
        );
    }

    @DisplayName("양방향 연관관계 주인이 아닌 객체 이용한 insert")
    @ParameterizedTest
    @MethodSource("getCategoryInfo")
    void slaveInsert (int categoryCode, String categoryName
                        ,Integer ref) {
        Category category = new Category(
                categoryCode
                ,categoryName
                ,ref // 이거 세개만 초기화하는 생성자는 없어서 만들어줌
        );

        service.registCategory(category);

        // insert 잘 됐다는 가정 하에
        Category foundCategory = service.findCategory(categoryCode);
        Assertions.assertNotNull(foundCategory);
    }

}
