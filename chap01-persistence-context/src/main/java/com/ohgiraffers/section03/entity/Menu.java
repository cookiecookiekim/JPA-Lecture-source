package com.ohgiraffers.section03.entity;
import jakarta.persistence.*;

// 만들어 놓은 공장 / 매니저로 crud 하기 위한 설정

// 엔티티를 관리하는 곳(persistent) 에 등록? 하겠다.
@Entity(name = "section03Menu")
@Table(name = "tbl_menu") // 실제 테이블 명과 일치
public class Menu { // pk가 반드시 필요
    // 인텔리 제이 data source에서 db 연결했음

    @Id // DB 상의 PK 역할을 하는 어노테이션
    // strategy 속성 IDENTITY = mysql의 auto-increment
    //              SEQUENCE = oracle의 auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_code")
    private int menuCode;   // 왼쪽에 열쇠 : pk 표시

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;

    // protected로 만든 이유 : 나중에 다른 Menu와 헷갈리지 않기 위해 이 패키지에서만 부르도록
    protected Menu() {}

    public Menu(String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    } // menuCode는 제외

    public Menu(int menuCode, String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        // test에 LifeCycleTest 클래스의 testNotManaged에서 사용
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", categoryCode=" + categoryCode +
                ", orderableStatus='" + orderableStatus + '\'' +
                '}';
    }
}
