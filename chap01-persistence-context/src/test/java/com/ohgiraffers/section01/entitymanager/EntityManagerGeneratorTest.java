package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManagerFactory;
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
    void testCreateFactory(){

        // ① main java에서 EntityManagerFactoryGenerator 생성하여 여기서 호출하는 식으로 진행
        EntityManagerFactory factory =

    }
}
