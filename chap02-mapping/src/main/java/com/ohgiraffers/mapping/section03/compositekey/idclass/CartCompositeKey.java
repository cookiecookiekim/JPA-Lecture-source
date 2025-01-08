package com.ohgiraffers.mapping.section03.compositekey.idclass;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CartCompositeKey { // 두 가지를 복합 키로 사용하고 싶을 때

    private int cartOwner;   // 카트 주인
    private int addedBook;  // 추가 된 책

}
