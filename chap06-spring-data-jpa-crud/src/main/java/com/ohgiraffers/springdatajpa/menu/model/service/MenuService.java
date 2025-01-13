package com.ohgiraffers.springdatajpa.menu.model.service;

import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.model.dao.MenuRepository;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    /* 페이징 처리하지 않은 메뉴 리스트 조회하기 */
    public List<MenuDTO> findeMenuList() {

        // 정렬 없는 findAll
//        List<Menu> menuList = repository.findAll(); // findAll이어서 예외 없어도 됨
        List<Menu> menuList = repository.findAll(Sort.by("menuPrice").descending());
                                                // 메뉴 가격 순으로 정렬돼서 출력

        // stream : 컬렉션 (List 등등) 데이터를 처리하기 위해 나열
        return menuList.stream()
                // map : 스트림화 된 데이터를 꺼내 매핑 및 변환
                .map(menu -> modelMapper.map(menu , MenuDTO.class))
                // collect : 스트림화 된 데이터를 다시 List로 변환
                .collect(Collectors.toList());
    }

    /* 페이징 처리를 한 메뉴 전체 조회 */
    public Page<MenuDTO> findeMenuListByPaging(Pageable pageable) {

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1
                ,pageable.getPageSize()
                ,Sort.by("menuCode").descending()
        );
        Page<Menu> menuList = repository.findAll(pageable);
        return menuList.map(
                menu -> modelMapper.map(menu, MenuDTO.class)
        );
    }
}
