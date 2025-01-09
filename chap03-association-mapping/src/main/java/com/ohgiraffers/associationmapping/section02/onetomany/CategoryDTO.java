package com.ohgiraffers.associationmapping.section02.onetomany;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CategoryDTO {

    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;

    private List<MenuDTO> menuList; // 여러가지 메뉴 가질 수 있도록 설정

}
