package com.ohgiraffers.springdatajpa.menu.model.service;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.model.dao.MenuRepository;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repository;
    // Bean으로 등록한 modelMapper 의존성 주입
    private final ModelMapper modelMapper;

    /* 1. 메뉴 코드로 특정 메뉴 조회하기 */
    public MenuDTO findMenuByMenuCode(int menuCode) {

        // 찾아와서 Entity화 시키기
        // 예외처리가 반드시 필요함
        Menu foundMenu = repository.findById(menuCode)
                                    // 강제로 예외 발생시키기
                                    // 잘못된 파라미터가 넘어왔을 때 예외처리 필수
                                   .orElseThrow(IllegalArgumentException::new);
//        if (foundMenu == null) {
//            throw new IllegalArgumentException("예외")
//        }  위의 식과 이 식이 동일하다는 뜻. 우린 orElseThrow 사용하면 될 듯..?

        // 컨트롤러는 DTO로 받아줘야 하므로 Entity(Menu)를 DTO 화 시켜야함
        // build.grale → BeanConfig에 설정

        // entity → dto
        // map(변환 대상, 변환할 타입)
        return modelMapper.map(foundMenu, MenuDTO.class);
    }
}
