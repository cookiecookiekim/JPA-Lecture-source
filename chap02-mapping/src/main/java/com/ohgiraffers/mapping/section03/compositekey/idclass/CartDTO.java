package com.ohgiraffers.mapping.section03.compositekey.idclass;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CartDTO {

    private int cartOwnerNo;
    private int addedBookNo;
    private int quantity;
}
