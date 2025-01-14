package com.ohgiraffers.springdatajpa.menu.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
//@Setter
//@Getter
@ToString
public class MenuDTO {

    // DTO는 view에서 넘어오는 , 혹은 넘겨줄 값으로
    // 필드를 구성하는 데이터를 전송하기 위한 객체이다.

    private int menuCode;
    private String menuName;
    private int menuPrice;
//    private int categoryCode;
    private CategoryDTO categoryDTO;
    private String orderableStatus;
    // db와 동일하게 맞춰줌.


    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public String getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(String orderableStatus) {
        this.orderableStatus = orderableStatus;
    }
}
