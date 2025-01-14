package com.ohgiraffers.springdatajpa.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity (name = "menu")
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter // @Setter 빼고 기본 세팅

//@Builder(toBuilder = true) // update 2번째 방식
public class Menu {

    @Id
    @Column(name = "menu_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

//    @Column(name = "category_code")
//    private int categoryCode;

    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn (name = "category_code")
    private Category category;

    @Column(name = "orderable_status")
    private String orderableStatus;

    // update 방법 1 :지양하지만 setter 함수로 메뉴 수정하는 방법
    // foundMenu.setMenuName(modifyMenu.getMenuName());
//    public void setMenuName(String menuName) {
//        this.menuName = menuName; // DTO의 menuName을 Entity화
//    }

    /* 3. Builder 패턴 직접 구현 */
//    public Menu menuName(String var){ // 변수 하나 받는다고 가정
//        this.menuName = var;
//        return this; // 예를들어 이름 들어오면 코드, 가격 등 동일한데 이름만 바뀐 채로 리턴
//                // 온 애들로 덮어 씌우고 리턴해주는 방식
//    }
//    public Menu builder(){
//        return new Menu(menuCode,menuName,menuPrice,categoryCode,orderableStatus);
//    }
}
