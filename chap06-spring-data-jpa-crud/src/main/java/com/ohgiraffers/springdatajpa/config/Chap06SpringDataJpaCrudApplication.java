package com.ohgiraffers.springdatajpa.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// application이 밖에 나와있으면 보기 슝해서 config에 가따가 박음
// → BeanCongig에 추가 설정!
@SpringBootApplication
public class Chap06SpringDataJpaCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chap06SpringDataJpaCrudApplication.class, args);
	}

}
