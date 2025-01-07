package com.ohgiraffers.section02.crud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

// main - java에서 만들어 놓은 애들이 잘 동작하는지 테스트 해보기
public class EntityManagerCRUDTest {

    /* comment.
    *   Test 클래스는 기존 Main 영역의 메서드를 테스트 하기 위함.
    *   따라서 검증할 클래스의 인스턴스를 생성하여 테스트 하는
    *   용도로 사용 된다. */

    // 위에서 만들어 놓은 EntityManagerCRUD 가져오기
    private EntityManagerCRUD crud;

    @BeforeEach
    void initManager() {
        // 테스트 메서드 실행 전 crud 클래스의 인스턴스 생성
        this.crud = new EntityManagerCRUD();
    }

    @AfterEach
    void rollback () {
        // 테스트가 실제 DB에 반영되지 않게 rollback 설정
        EntityTransaction transaction // 매니저가 트랜잭션 관리하게
                = crud.getManagerInstance().getTransaction();

        // 모든 권한은 매니저에게 entity를 관리하게 넘겨준다.
//        transaction.rollback();
    }

    /* comment.
        테스트 시 여러 값들을 이용하여 검증이 필요한 경우,
        경우의 수 별로 테스트 메서드를 작성해야 한다.
        ParameterizedTest는 경우의 수 만큼 반복해야 할 작업을 보여줄 수 있다.
        파라미터로 전달할 값을 목록 만큼 반복적으로 테스트 메서드를 실행 해준다. */
    @ParameterizedTest
    @DisplayName("menuCode 넘겨주고 select 문 조회하기")
    // 여러 개의 테스트 전용 파라미터를 전달. 쉼표로 값을 구분한다.
    @CsvSource({"1,1", "2,2", "3,3"})
    void testFindByCode(int menuCode, int expected){

        Menu foundMenu = crud.findMenuByMenuCode(menuCode);

        System.out.println("foundMenu = " + foundMenu);

        Assertions.assertEquals(expected, foundMenu.getMenuCode()); // expected와 getMenuCode 일치하는지
        // run 시 에러 → 공장에 Menu를 등록해줘야 함.
        // → persistence.xml에 <class>com.ohgiraffers.section02.crud.Menu</class> 등록
    }

    private static Stream<Arguments> newMenu(){ // 전달 인자 한 꺼번에 뭉쳐서 전달하기
        return Stream.of(
                Arguments.of(
                        "불고기백반"
                        ,12000
                        ,4
                        ,"Y"
                )
        );
    }

    @ParameterizedTest
    @DisplayName("새로운 메뉴 insert Test")
    @MethodSource("newMenu") // 메서드로도 전달 가능 (위의 메서드 정보 받아옴)
    void testInsertNewMenu (String name, int price, int code, String orderableStatus) {
        Menu newMenu = new Menu(name, price, code, orderableStatus);
        System.out.println("newMenu = " + newMenu);

        // 인서트 성공 시 1 반환 받으므로, Long count라 명명
        Long count = crud.saveAndReturnCount(newMenu);

        // 현재 전체 메뉴가 21개 있으므로 새로운 메뉴 22와 count가 일치하는지 확인
        Assertions.assertEquals(22, count);
    }

    @ParameterizedTest
    @DisplayName("메뉴 이름 수정 테스트")
    @CsvSource("25, 우삼겹백반") // 25번을 우삼겹백반으로 수정하기
    void modifyTestMenu (int code, String name) {
        Menu modifyMenu = crud.modifyMenuName(code,name);

        Assertions.assertEquals(name, modifyMenu.getMenuName()); // name과 수정 됐을 때 이름이 일치하는지 확인
    }

    @ParameterizedTest
    @DisplayName("메뉴 코드로 메뉴 삭제")
    @ValueSource(ints = {25})
    void testRemoveMenu (int code) {
        Long count = crud.removeAndReturnCount(code);

        Assertions.assertEquals(22, count); // 25번 삭제하고 전체 개수가 22개가 맞는지
    }

}
