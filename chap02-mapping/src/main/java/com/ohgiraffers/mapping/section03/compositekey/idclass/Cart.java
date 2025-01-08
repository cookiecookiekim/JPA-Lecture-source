package com.ohgiraffers.mapping.section03.compositekey.idclass;

import jakarta.persistence.*;

// 25-01-08 5교시, 장바구니 관련 엔티티

@Entity
@Table(name = "tbl_cart")
@IdClass(CartCompositeKey.class) // 복합키로 설정할 클래스 명시
public class Cart {

    @Id
    @Column(name = "cart_owner")
    private int cartOwner;
    // CartCompositeKey에 작성한 필드명과 동일하게 작성해야함

    @Id
    @Column(name = "added_book")
    private int addedBook;
    // CartCompositeKey에 작성한 필드명과 동일하게 작성해야함

    @Column(name = "quantity")
    private int quantity; // 수량

    public Cart(){}

    public Cart(int cartOwner, int addedBook, int quantity) {
        this.cartOwner = cartOwner;
        this.addedBook = addedBook;
        this.quantity = quantity;
    }
}
