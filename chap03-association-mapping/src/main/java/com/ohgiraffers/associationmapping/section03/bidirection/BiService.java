package com.ohgiraffers.associationmapping.section03.bidirection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BiService {

    @Autowired
    private BiRepository repository;

    public Menu findMenu(int menuCode) {
        return repository.findMenu(menuCode);
    }

    // oneToMany 관계는 지연로딩이 default이기 때문에 설정
    @Transactional
    public Category findCategory(int categoryCode) {

        Category foundCategory = repository.findCategory(categoryCode);
        // 찾았다는 가정 하에 출력해보기
        System.out.println("foundCategory.getMenuList():"+foundCategory.getMenuList());

        return foundCategory;
    }

    @Transactional
    public void registMenu(Menu newMenu) {
        repository.save(newMenu);
    }

    @Transactional
    public void registCategory(Category category) {
        repository.saveCategory(category);
    }
}
