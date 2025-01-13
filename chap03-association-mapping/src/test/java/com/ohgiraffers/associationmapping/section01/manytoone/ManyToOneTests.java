package com.ohgiraffers.associationmapping.section01.manytoone;

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
public class ManyToOneTests {

    @Autowired
    private ManyToOneService service;

    /* title. 연관관계 매핑 */
    /* comment. Entity 클래스 간의 관계 == 연관성 매핑하는 것을 의미
     *   이를 통하여 객체를 이용한 DB 테이블 간 관계를 매핑할 수 있다. */

    /* comment. 다중성에 의한 분류
     *   연관 관계가 있는 객체 관계에서 실제로 연관을 가지는
     *   객체 의 수에 따라 분류된다.
     *   - 1:1 (OneToOne)
     *   - 1:N (OneToMany)
     *   - N:1 (ManyToOne)
     *   - M:M (ManyToMany) */

    /* comment. ManyToOne
     *   ManyToOne은 다수의 엔티티(메뉴)가 하나의 엔티티(카테고리)를
     *   참조하는 상황에서 사용된다.
     *   예를 들자면 하나의 카테고리가 여러 개의 메뉴를 가질 수 있는
     *   상황에서 메뉴가 카테고리를 참조하는 것.
     *   따라서 이 때 Menu가 Many, Category가 One이 된다. */

    /* comment. 연관관계 형성된 엔티티를 조회하는 방법
     *   1. 객체 그래프 탐색(객체 연관 관계를 이용한 조회)
     *   2. 객체 지향 쿼리(JPQL) 사용이 있다. */

    @DisplayName("N:1 관계의 객체 그래프 탐색을 이용한 조회")
    @Test
    void manyToOneFind() {
        int menuCode = 10; // 테스트 위해 값 주입

        Menu foundMenu = service.findMenu(menuCode);

        // 조회 잘 해왔다는 가정 하에
        Category category = foundMenu.getCategory();

        System.out.println("category = " + category);

        Assertions.assertNotNull(category);
    }

    @DisplayName("N:1 연관 관계 객체지향 쿼리 이용 테스트")
    @Test
    void manyToOneJPQL() {
        int menuCode = 11;

        // 카테고리 전부가 아니라 이름만 빼와보기
        String categoryName = service.findCategoryName(menuCode);

        System.out.println("categoryName = " + categoryName);

        Assertions.assertNotNull(categoryName);
    }

    /* comment. commit()을 하게 될 경우 엔티티 객체를
                insert 하는 쿼리문이 동작하게 된다.
          단, 고려할 점은 영속성 전이이다.
          메뉴 엔티티를 insert 시 PERSIST 속성으로 인해
          카테고리 엔티티도 insert를 시켜야 한다는 의미이다. */

    private static Stream<Arguments> getInfo() {
        return Stream.of(Arguments.of(100, "돈까스덮밥",
                30000, 123, "퓨전분식", "Y")
        );
    }

    @DisplayName("N:1 연관관계 객체 삽입 테스트")
    @ParameterizedTest
    @MethodSource("getInfo")
    void manyToOneInsert(int menuCode, String menuName, int menuPrice
                        ,int categoryCode, String categoryName
                        ,String orderableStatus){

        MenuDTO menuInfo = new MenuDTO(
                menuCode, menuName,menuPrice,
                new CategoryDTO(categoryCode,categoryName,null)
                ,orderableStatus
        );
        Assertions.assertDoesNotThrow(
                () -> service.registMenu(menuInfo)
        ); // insert 시, category가 먼저 insert 되고 그 이후에 menu insert
    }
}
