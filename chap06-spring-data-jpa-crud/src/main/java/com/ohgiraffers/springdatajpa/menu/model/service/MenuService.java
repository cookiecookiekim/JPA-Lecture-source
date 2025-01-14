package com.ohgiraffers.springdatajpa.menu.model.service;

import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.model.dao.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.model.dao.MenuRepository;
import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repository;
    // Bean으로 등록한 modelMapper 의존성 주입
    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

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
    public Page<MenuDTO> findMenuListByPaging(Pageable pageable) {

        pageable = PageRequest.of( // 페이지 번호를 0 기반으로 조정
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1
                ,pageable.getPageSize() // 한 페이지에 포함될 데이터 개수
                ,Sort.by("menuCode").descending() // 내림차수 정렬
        );
        Page<Menu> menuList = repository.findAll(pageable);
        return menuList.map(
                menu -> modelMapper.map(menu, MenuDTO.class)
        );
    }

    public List<MenuDTO> findByMenuPrice(int menuPrice) {
                  // 해당 가격을 초과하는 애들 찾는... 메서드 구문 (JPA 제공 메서드)
        List<Menu> menuList = repository.findByMenuPriceGreaterThanOrderByMenuPrice(menuPrice);
        // 단점이 조건 증가할 경우 메서드명이 엄청 길어지는데 가독성이 안 좋음
        // → 그럴 경우에는 native query를 이용하여 직접 작성하는 게 낫다.
        return menuList.stream().map(
                menu -> modelMapper.map(menu, MenuDTO.class))
                                    .collect(Collectors.toList());
    }

    public List<CategoryDTO> findAllCategory() {

        List<Category> categoryList
                = categoryRepository.findAllCategory();
        System.out.println("서비스 categoryList = " + categoryList);
        return categoryList.stream().map(
                category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

//    @Transactional // 이건 내가 한 인서트 방식
//    public void registNewMenu(MenuDTO newMenu) {
//
//        Menu insertMenu = new Menu(newMenu.getMenuCode(),
//                newMenu.getMenuName(), newMenu.getMenuPrice(),
//                newMenu.getCategoryCode(),
//                newMenu.getOrderableStatus());
//        // save 메서드를 통해 insert 시 정수 값 반환되지 않음
//        // 정수값 반환 받으려면 직접 native 쿼리로 작성해야 함
//        repository.save(insertMenu);
//    }

    @Transactional // 25-01-14 (화) 1교시
    public void registNewMenu(MenuDTO newMenu) {

        // 지금까지와 반대로 DTO타입을 Entity로 변환
        // DML 구문에서는 DTO타입을 Entity로 변환해야
        // Persistence Context == JPA 가 관리를 해준다.
        repository.save(modelMapper.map(newMenu, Menu.class));
    }

    @Transactional // 인서트하고 정수 반환받아보고 싶어서 해봤는데 비효율적.. 컷
    public int registNewMenu2(MenuDTO newMenu) {

        Menu insertMenu2 = new Menu(
                newMenu.getMenuCode(),
                newMenu.getMenuName(),
                newMenu.getMenuPrice(),
                newMenu.getCategoryCode(),
                newMenu.getOrderableStatus()
        );

        System.out.println("insertMenu2 = " + insertMenu2);
        int result = repository.insertNewMenu2(insertMenu2);

        System.out.println("result = " + result);
        return result >= 1 ? 1 : 0;
    }

    @Transactional
    public void deleteService(int menuCode) {

        Menu menu = repository.findById(menuCode).orElseThrow();
        repository.delete(menu);
    }

    @Transactional
    public void modifyMenu(MenuDTO modifyMenu) {
        // JPA에서는 update가 필요 없음 → Entity에서 바꿔주면 됨.
        // 하나의 메뉴를 특정하기 위해 menuCode 이용

        /* update는 엔티티를 특정해서 필드의 값을 변경해주면 된다. */
        /* JPA는 변경 감지 기능이 있으므로 하나의 엔티티를 특정하여
        *   필드 값을 변경하면 변경된 값으로 flush(반영) 해줌. */

        // 엔티티 찾기(특정하기) (modifyMenuDTO에 있는 menuCode 꺼내기)
        Menu foundMenu =
                repository.findById(modifyMenu.getMenuCode())
                          .orElseThrow(IllegalArgumentException::new);
                        // menuCode가 없으면 예외 발생 시킬거다.

        System.out.println("특정된 메뉴(찾은 Entity 값) = " + foundMenu);
        /* 값 수정하는 방식
        * 1. setter를 통한 update - 지양 방식(누구나 접근할 수 있으므로) */
//        foundMenu.setMenuName(modifyMenu.getMenuName());
//        System.out.println("setter 사용 후 foundMenu = " + foundMenu);
        // → menuName만 수정된 이름으로 변경

        /* 2. @Builder를 통해 update 기능 : Menu → @Builder */
    }
}
