package com.ohgiraffers.springdatajpa.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// application이 밖에 나와있으면 보기 슝해서 config에 가따가 박음
// → BeanCongig에 추가 설정!
@SpringBootApplication

// app 옮겨서 이걸 해줘야 run시 MenuRepository를 찾을 수 있다.
@EnableJpaRepositories (basePackages = "com.ohgiraffers.springdatajpa")

// 얘도 app 옮겨서 해줘야 entity를 찾을 수 있다
@EntityScan(basePackages = "com.ohgiraffers.springdatajpa")
public class Chap06SpringDataJpaCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(Chap06SpringDataJpaCrudApplication.class, args);
	}

}
