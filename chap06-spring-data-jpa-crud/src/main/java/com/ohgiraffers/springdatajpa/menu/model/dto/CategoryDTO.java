package com.ohgiraffers.springdatajpa.menu.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
//@Setter
//@Getter
@ToString
public class CategoryDTO {

    private int categoryCode;
    private String categoryName;
//    private Integer refCategoryCode;

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

//    public Integer getRefCategoryCode() {
//        return refCategoryCode;
//    }
//
//    public void setRefCategoryCode(Integer refCategoryCode) {
//        this.refCategoryCode = refCategoryCode;
//    }
}
