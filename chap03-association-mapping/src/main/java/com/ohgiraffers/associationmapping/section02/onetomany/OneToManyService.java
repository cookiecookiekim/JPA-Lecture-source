package com.ohgiraffers.associationmapping.section02.onetomany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OneToManyService {

    @Autowired
    private OneToManyRepository repository;

    @Transactional(readOnly = true) // select 구문인데도 Transactional
    // 이른로딩 / 지연로딩을 해결하는 방법 readOnly
    public Category findCategory(int categoryCode) {

        Category category = repository.find(categoryCode);
        System.out.println("category = " + category);
        return category;
    }
}
