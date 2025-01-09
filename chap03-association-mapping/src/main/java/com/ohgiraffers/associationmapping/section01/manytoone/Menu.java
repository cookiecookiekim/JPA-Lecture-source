package com.ohgiraffers.associationmapping.section01.manytoone;

import jakarta.persistence.*;
import lombok.*;

// 25-01-09 (목) 1교시, N:1 연관 관계
// N인 메뉴 기준으로 1인 카테고리와 조인하는 상황
@Entity(name = "menu_and_category") // 섹션별로 생성할 거므로 헷갈리지 않게 이름 설정
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter // 엔티티는 함부로 수정하는 건 좋지 않으므로 Setter는 제외하고 Getter만 가져오기
@ToString
public class Menu {

    @Id
    @Column(name = "menu_code")
//    @GeneratedValue(strategy = GenerationType.IDENTITY) none 해놔서 이거 있으면 에러
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    /* comment. [영속성 전이]
     *   특정 엔티티를 영속화(관리)할 때, 연관관계에 있는
     *   엔티티도 함께 영속화 한다는 의미.
     *   해당 클래스에서 예를 들면 1번 메뉴를 영속화 할 때,
     *   포함하는 Category 엔티티도 함께 영속화 한다는 의미. */

    @ManyToOne(cascade = CascadeType.PERSIST)
    // 메뉴 조회할 때, Category(code)도 영속화(관리) 하겠다.
    @JoinColumn(name = "category_code") // category_code와 join 할 거다
    // 조회 하려는 Id 컬럼을 name에 입력
    private Category category;

    @Column(name = "orderable_status")
    private String orderableStatus;
}
