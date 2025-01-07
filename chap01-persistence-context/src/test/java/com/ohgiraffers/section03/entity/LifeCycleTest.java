package com.ohgiraffers.section03.entity;

import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class LifeCycleTest {

    private EntityLifeCycle lifeCycle;

    @BeforeEach
    void setLifeCycle() { // 실행 될 때마다 인스턴스 생성
        this.lifeCycle = new EntityLifeCycle();
    }

    @ParameterizedTest
    @DisplayName("비영속 테스트(관리하지 않음)") // 뭔짓을 해도 DB에 반영되지 않음
    @ValueSource(ints = {1, 2})
    void testNotManaged(int menuCode){
        Menu foundMenu = lifeCycle.findMenuByMenuCode(menuCode);

        Menu newMenu = new Menu( // foundMenu에서 찾아온 걸 꺼내줌.
                                // 모든 필드를 초기화 하는 생성자 필요.
                foundMenu.getMenuCode()
                ,foundMenu.getMenuName()
                ,foundMenu.getMenuPrice()
                , foundMenu.getCategoryCode()
                ,foundMenu.getOrderableStatus()
        );

        Assertions.assertNotEquals(foundMenu , newMenu); // 둘이 다르다!
    }

    @ParameterizedTest
    @DisplayName("다른 엔티티 매니저가 관리하는 영속성 테스트")
    @ValueSource(ints = {1, 2})
    void testOtherManager(int menuCode){ // 아까 매니저 생성 안 했는데 DML 됐는데 그거 테스트
        Menu menu1 = lifeCycle.findMenuByMenuCode(menuCode);
        Menu menu2 = lifeCycle.findMenuByMenuCode(menuCode);

        Assertions.assertEquals(menu1, menu2); // 이것도 주소 확인해보면 @ 뒤에 주소가 다르다
        // Expected :com.ohgiraffers.section03.entity.Menu@62faf77
        // <Menu{menuCode=1, menuName='열무김치라떼', menuPrice=4500, categoryCode=8, orderableStatus='Y'}>
        // Actual   :com.ohgiraffers.section03.entity.Menu@74a85515
        // <Menu{menuCode=1, menuName='열무김치라떼', menuPrice=4500, categoryCode=8, orderableStatus='Y'}>
    }

    @ParameterizedTest
    @DisplayName("같은 엔티티 매니저가 관리하는 영속성 테스트")
    @ValueSource(ints = {1, 2})
    void testSameManager(int menuCode){
        // 매니저 가져 오는 게 아니라 여기서 만들기
        EntityManager manager = EntityManagerGenerator.getInstance();

        Menu menu1 = manager.find(Menu.class , menuCode);
        Menu menu2 = manager.find(Menu.class , menuCode);

        Assertions.assertEquals(menu1 , menu2); // 같은 매니저로 조회 시, 같다.
    }

    /* comment.
    *   엔티티 매니저가 영속성 컨텍스트에 엔티티 객체를 저장 (persist)
    *   하게 된다면 영속성 컨텍스트가 관리될 수 있게 되며, 이를 영속 상태라고 한다.
    *   find() , createQuery()를 사용한 조회도 자동적으로 영속 상태가 됨.
    *   영속 상태인 엔티티 객체는 PK로 조회를 하면 이미 관리가 되고 있기 때문에
    *   같은 객체(인스턴스)를 반환하게 된다.*/

    @ParameterizedTest
    @DisplayName("준영속화 detach 테스트")
    @CsvSource({"11, 1000", "12, 1000"})
    void testDetachEntity(int menuCode, int menuPrice){
        EntityManager manager = EntityManagerGenerator.getInstance();

        EntityTransaction transaction
                = manager.getTransaction(); // 트랜잭션도 열어줘보기.

        Menu foundMenu = manager.find(Menu.class, menuCode);

        transaction.begin();

        // detach : 특정 엔티티만 준영속 상태(관리하지 않은 상턔)
        manager.detach(foundMenu); // 영속 컨택스트에서 빼겠다.
        // 이거 주석하면 관리를 안 하므로, update문 돌아가지 않음.

        // 이미 정의돼있는 가격을 전달받은 가격으로 변경해보기
        foundMenu.setMenuPrice(menuPrice);
        manager.flush(); // set 했으니 반영해달라고 flush
        Assertions.assertEquals(menuPrice, manager.find(Menu.class,menuCode).getMenuPrice());
                                        // foundMenu.getMenuPrice()라고 적으면 같다고 나옴
        transaction.rollback();
    }
}
