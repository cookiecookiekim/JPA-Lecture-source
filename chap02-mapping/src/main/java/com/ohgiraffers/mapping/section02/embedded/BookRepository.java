package com.ohgiraffers.mapping.section02.embedded;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

    @PersistenceContext // EntityManager 불러오기
    private EntityManager manager;

    public void save(Book book) {
        manager.persist(book);
    }
}
