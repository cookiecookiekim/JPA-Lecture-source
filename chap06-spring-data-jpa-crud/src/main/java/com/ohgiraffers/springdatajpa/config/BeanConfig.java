package com.ohgiraffers.springdatajpa.config;

//application을 config로 옮기면 scan 기능이 config밖에 안 돼서 여기서 추가 설정

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.ohgiraffers.springdatajpa") // springdatajpa 하위 다 읽을 수 있게 설정
public class BeanConfig {

    @Bean // build.gradle에 의존성 넣고 추가 설정 해줘야함
    public ModelMapper modelMapper () {
        /* comment.
        *   entity 클래스는 데이터의 무결성을 유지하기 위해 setter 사용 지양.
        *   그렇다면 dto 값을 어떻게 entity에 set(넣을까)할까?
        *   modelmapper는 dto ↔ entity간 변환을 용이하게 하는 라이브러리. */

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration() // 설정 가져오기
                // private 필드에 접근하기 위한 설정
                // 가져온 설정을 다시 바꿔주기
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                // DTO 필드와 Entity 필드 매칭 가능 여부 설정
                .setFieldMatchingEnabled(true);

        return modelMapper;
    }
}
