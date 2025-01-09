package com.ohgiraffers.associationmapping.section02.onetomany;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity(name = "category_and_menu") // 카테고리 위주(기준)로 확인
@Table(name = "tbl_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Category {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    @OneToMany(cascade = CascadeType.PERSIST) // 카테고리 코드에 대응하는 메뉴들 영속화 작업
    @JoinColumn(name = "category_code") // 카테고리 코드 기준으로 조인
    private List<Menu> menuList;
}
