package com.ohgiraffers.springdatajpa.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter // @Setter 빼고 기본 세팅

@Builder(toBuilder = true) // update 2번째 방식
public class Menu {

    @Id
    @Column(name = "menu_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;

    // 지양하지만 setter 함수로 메뉴 수정하는 방법
    // foundMenu.setMenuName(modifyMenu.getMenuName());
    public void setMenuName(String menuName) {
        this.menuName = menuName; // DTO의 menuName을 Entity화
    }
}
