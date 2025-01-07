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

    @ParameterizedTest
    @DisplayName("준영속화 detach 후 재 영속화(merge) 테스트")
    @CsvSource({"11, 1000", "12, 1000"})
    void testDetachAndPersist(int menuCode, int menuPrice){ // detach 했던 걸 다시 관리하겠다.
        EntityManager manager = EntityManagerGenerator.getInstance();

        EntityTransaction transaction
                = manager.getTransaction(); // 트랜잭션도 열어줘보기.

        Menu foundMenu = manager.find(Menu.class, menuCode);

        transaction.begin();

        // detach : 특정 엔티티만 준영속 상태(관리하지 않은 상턔)
        manager.detach(foundMenu); // 영속 컨택스트에서 빼겠다.
        foundMenu.setMenuPrice(menuPrice);

        // merger : 관리하지 않는 엔티티를 다시 관리 요청
        manager.merge(foundMenu);
        /* comment.
        *   파라미터로 넘어온 foundMenu 준영속 엔티티 객체의 식별자 값으로
        *   1차 캐시에서 조회, 만약 1차 캐시에 엔티티가 없으면
        *   DB에서 엔티티 조회 후 1차 캐시에 저장한다.
        *   조회한 영속 엔티티 객체에 준영속 상태의 엔티티 객체의 값을 병합(merge)
        *   한 뒤 영속 엔티티 객체를 반환하게 된다. */
        manager.flush();

        Assertions.assertEquals(menuPrice, manager.find(Menu.class,menuCode).getMenuPrice());
        // foundMenu.getMenuPrice()라고 적으면 같다고 나옴
        transaction.rollback();
    }

    @ParameterizedTest
    @DisplayName("영속성 엔티티 삭제 remove 테스트")
    @ValueSource(ints = {1})
    void testRemoveEntity(int menuCode){
        EntityManager manager = EntityManagerGenerator.getInstance();

        EntityTransaction transaction
                = manager.getTransaction();

        // 매니저에게 메뉴 코드로 메뉴 찾아달라고 하기
        Menu foundMenu = manager.find(Menu.class , menuCode);

        transaction.begin();

        /* comment. remove() 엔티티를 영속성 컨텍스트 및 DB에서 제거한다.
        *   단, 트렌젝션을 제어하지 않으면 DB에 영구 반영되지는 않는다.
        *   트렌잭션을 커밋, 플러시 하는 순간 영속성 컨텍스트에서
        *   관리하는 엔티티 객체를 DB에 반영하게 된다. */

        manager.remove(foundMenu);

        // remove 반영을 위한 flush
        manager.flush();
        // DB에 전달은 되지만, commit하지는 않았으므로 실제 반영은 안 됨!!
        // commit에 flush가 내부적으로 포함돼 있기 때문에 db 저장할거면
        // 굳이 flush 안 하고 commit만 해도 됨.

        // 같은 메뉴 코드로 동일한 데이터 찾아지는지 확인
        Menu reFoundMenu = manager.find(Menu.class, menuCode);
        // 삭제가 반영 됐을 거라 예상

        Assertions.assertNull(reFoundMenu); // reFoundMenu가 null인지 확인
    }

}
