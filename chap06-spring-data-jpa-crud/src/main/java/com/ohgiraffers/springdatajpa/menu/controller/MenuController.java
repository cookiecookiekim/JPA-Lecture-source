package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.model.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/menu")
// 필드 주입보다 생성자 주입이 안전한데 그때 사용하는 유용한 어노테이션
@RequiredArgsConstructor // 필드에 final 키워드가 붙으면 자동으로 생성자 주입 해줌
@Slf4j // Lombok 라이브러리에서 제공하는 로깅 관련 어노테이션
public class MenuController {

    // RequiredArgsConstructor 넣어서 Autowired도 자동 주입
    private final MenuService menuService;

//    public MenuController (MenuService menuService) {
//        this.menuService = menuService;
//    } → @RequiredArgsConstructor 이걸 해놨기 때문에 생성자 작성 안 해도 됨.

    // 경로를 타고 넘오오는 변수로 받기
    @GetMapping("/{menuCode}") // PathVariable
    public String findMenuByPathVariable(@PathVariable int menuCode,Model model){
        // entity가 dto로 변한 값을 resultMenu에 대입
        MenuDTO resultMenu = menuService.findMenuByMenuCode(menuCode);

        model.addAttribute("result", resultMenu);
        return "menu/detail";
    }

    @GetMapping("/list") // 메뉴 전체 조회하기
    public String findAllMenu (Model model , @PageableDefault Pageable pageable) {
        // 페이징 처리하지 않은 findAll();
//        List<MenuDTO> menuList = menuService.findeMenuList();
//        model.addAttribute("menus", menuList);

        // 매개 변수로 담는 pageable에 뭐가 담겨 있을까 log
        log.info("pageable : {}" , pageable);

        Page<MenuDTO> menuList = menuService.findeMenuListByPaging(pageable);

        log.info("조회한 내용 목록 : {}", menuList.getContent());
        log.info("총 페이지 수 : {}", menuList.getTotalPages());
        log.info("총 메뉴의 수 : {}", menuList.getTotalElements());
        log.info("해당 페이지에 표현될 요소의 수 : {}", menuList.getSize());

        model.addAttribute("menuList", menuList);
        return "menu/list";
    }
}
