package com.ohgiraffers.associationmapping.section01.manytoone;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class MenuDTO {

    private int menuCode;
    private String menuName;
    private int menuPrice;
    private CategoryDTO categoryDTO;
    private String orderableStatus;
}
