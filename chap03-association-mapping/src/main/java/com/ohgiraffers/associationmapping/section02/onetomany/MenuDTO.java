package com.ohgiraffers.associationmapping.section02.onetomany;

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
    private int categoryCode; // 수정
    private String orderableStatus;
}
