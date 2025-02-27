package com.ohgiraffers.springdatajpa.common;

import lombok.*;

// pasing 처리를 위한 설정 클래스
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PagingButton {

    private int currentPage; // 현재 페이지
    private int startPage;  // 시작 페이지
    private int endPage;    // 마지막 페이지

}
