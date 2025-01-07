package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.sql.ast.tree.update.Assignable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// 엔터티 관리하는 매니저 생성
// 25-01-07 (1교시)
public class EntityManagerGeneratorTest {

    /* title. Persistence Context를 이해하기 위한 엔티티 매니저와 팩토리 */

    /* comment. 엔티티 매니저 팩토리란?
     *   엔티티 매니저를 생성할 수 있는 기능을 제공하는 클래스.
     *   팩토리는 thread 에 안전하기 때문에(동시성) 하나의 인스턴스를
     *   생성하여 application 스코프와 동일하게 관리 == singleton */

    @Test
    @DisplayName("엔티티 매니저 팩토리 생성 확인하기")
    void testCreateFactory() {

        // ① main java에서 EntityManagerFactoryGenerator 생성하여 여기서 호출하는 식으로 진행
        EntityManagerFactory factory = EntityManagerFactoryGenerator.getInstance(); // 공장 1

        EntityManagerFactory factory2 = EntityManagerFactoryGenerator.getInstance(); // 공장 2

        // 생성된 공장이 싱글톤인지 확인
        System.out.println("factory1.hashCode() = " + factory.hashCode()); // 1694102613
        System.out.println("factory2.hashCode() = " + factory2.hashCode()); // 1694102613

        // 팩토리가 생성이 잘 되는 지 확인(검증)
        Assertions.assertNotNull(factory);

        // 팩토리 인스턴스가 singleton 인지 확인
        Assertions.assertEquals(factory, factory2);
    }

    /* comment. 엔티티 매니저란?
     *   엔티티 매니저는 엔티티를 저장하는 메모리 상의 DB를 관리하는 인스턴스
     *   저장 , 수정 , 삭제 , 조회 등의 엔티티 관련 모든 일을
     *   factory 내부에서 진행하게 됨.
     *   엔티티 매니저는 동시성에 안전하지 못하기 때문에
     *   1개의 요청 시 매니저를 생성하게 함 (request-scope)와 일치 */

    @Test
    @DisplayName("엔티티 매니저 생성 확인") // 매니저는 만들때마다 생성 (싱글톤 아님)
    void testCreateManager() {
        EntityManager manager =
                EntityManagerGenerator.getInstance();

        EntityManager manager2 =
                EntityManagerGenerator.getInstance();

        // hashCode 같은지 확인
        System.out.println("manager.hashCode() = " + manager.hashCode()); // 1874901958
        System.out.println("manager2.hashCode() = " + manager2.hashCode()); // 1620253123

        // 매니저가 잘 생성되는지 test
        Assertions.assertNotNull(manager);

        // 서로 다른 인스턴스가 맞는지 test
        // 둘이 서로 같지 않으면 true → test 통과
        Assertions.assertNotEquals(manager , manager2); // true → 만들 때 마다 생성되니까
    }
}
