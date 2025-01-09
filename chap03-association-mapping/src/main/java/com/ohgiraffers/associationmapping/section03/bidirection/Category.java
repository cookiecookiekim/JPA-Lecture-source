package com.ohgiraffers.associationmapping.section03.bidirection;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

// 4교시, 양방향 관계
@Entity(name = "bi_category")
@Table(name = "tbl_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
//@ToString // test run 시, 순환참조이므로 OneToMany 쪽 ToString 주석해야 돌아감
public class Category {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    /* comment. 현재,
    *   메뉴 → 카테고리 참조 / 카테고리 → 메뉴 참조 : 양방향 관계
    *   객체의 참조는 둘인데, 외래키가 하나인 상황을 해결하기 위해,
    *   두 연관관계 중 하나를 정해 테이블의 외래키를 관리한다.
    *   이를 연관관계의 주인(Owner)라고 한다.
    *   mappedBy 속성은 연관관계의 주인이 아닌 쪽(외래키 없는쪽)에
    *   사용되며, 주인 엔티티의 연관된 필드를 가리킨다. */

    @OneToMany(mappedBy = "category") //
    private List<Menu> menuList;

    public Category(int categoryCode, String categoryName, Integer ref) {
        // slaveInsert 이거 용 생성자
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = ref;
    }
}
