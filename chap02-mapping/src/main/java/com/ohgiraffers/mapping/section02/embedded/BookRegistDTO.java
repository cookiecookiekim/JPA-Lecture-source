package com.ohgiraffers.mapping.section02.embedded;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class BookRegistDTO { // DTO는 화면에서 받아올 데이터 위주로 구성
    // Entity는 반대 개념.
    private String bookTitle;
    private String author;
    private String publisher;
    private LocalDate createdDate;
    private int regularPrice;
    private double discountRate;

}
