package com.ohgiraffers.springdatajpa.menu.model.dao;

import com.ohgiraffers.springdatajpa.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /* 여기서는 직접 쿼리문을 작성해 보는 연습 */
    @Query(value = "SELECT * FROM TBL_CATEGORY ORDER BY CATEGORY_CODE",
    nativeQuery = true) // nativeQuery라는 걸 알려줘야 함.
    List<Category> findAllCategory();

}
