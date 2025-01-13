package com.ohgiraffers.springdatajpa.common;

import org.springframework.data.domain.Page;

public class Pagenation {

    // 공통적으로 사용할 페이징 정보 기술
    // ex) 첫 페이지 번호 , 페이지에 해당하는 데이터 수 등등
    public static PagingButton getPagingInfo(Page page) {
                     // Page : Spring에서 제공하는 Interface import

        // 현재 페이지에 대한 정보
        // +1 하는 이유 : 0부터 시작이므로 사용자 시점에서 보기 편하게
        int currentPage = page.getNumber() + 1;

        // 페이지 버튼의 기본 개수
        int defaultButtonCount = 10; // 1~10 or 2~11 ....

        // 현재 시작 페이지 계산
        // Math.ceil((double)currentPage / defaultButtonCount) -1 이 부분 항상 0
        // → 시작 페이지가 항상 1이라는 뜻.
        int startPage = (int) (Math.ceil((double)currentPage / defaultButtonCount) -1)
                        * defaultButtonCount + 1;

        int endPage = startPage + defaultButtonCount - 1;

        // 실제 총 페이지가 endPage보다 작을 경우 endPage를 총 페이지 수로 지정
        if(page.getTotalPages() < endPage){
            // 예를들어 데이터 60개 밖에 없는데 10페이지까지 보여줄 필요 없으므로
            endPage = page.getTotalPages();
        }
        // 페이지가 0이면 끝 페이지를 시작 페이지로 설정
        // 예를 들어 데이터가 6개 밖에 없는데 페이지를 보여줄 필요 없음
        if(page.getTotalPages() == 0 && endPage == 0){
            endPage = startPage;
        }
        // 계산된 정보들을 반환 (Page 관련 정보)
        return new PagingButton(currentPage,startPage,endPage);
    }

}
