package com.ohgiraffers.associationmapping.section01.manytoone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManyToOneService {

    @Autowired
    private ManyToOneRepository repository;
}
