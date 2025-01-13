package com.ohgiraffers.springdatajpa.config;

//application을 config로 옮기면 scan 기능이 config밖에 안 돼서 여기서 추가 설정

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.ohgiraffers.springdatajpa") // springdatajpa 하위 다 읽을 수 있게 설정
public class BeanConfig {
}
