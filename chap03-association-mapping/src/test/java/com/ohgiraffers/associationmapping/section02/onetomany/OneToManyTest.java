package com.ohgiraffers.associationmapping.section02.onetomany;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OneToManyTest {

    @Autowired
    private OneToManyService service;

    @DisplayName("1:N 연관관계 객체 그래프 탐색 조회")
    @Test
    void oneToManyFind(){ // 예를 들어 내가 쓴 게시글 조회 (1:N)
        int categoryCode = 10; // 카테고리 기준으로 검색

        Category category = service.findCategory(categoryCode);

        System.out.println(category.getMenuList());

        Assertions.assertNotNull(category);
    }
}
