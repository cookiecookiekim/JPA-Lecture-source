package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.common.PagingButton;
import com.ohgiraffers.springdatajpa.common.Pagenation;
import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.model.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String findMenuByPathVariable(@PathVariable int menuCode, Model model) {
        // entity가 dto로 변한 값을 resultMenu에 대입
        MenuDTO resultMenu = menuService.findMenuByMenuCode(menuCode);

        model.addAttribute("result", resultMenu);
        return "menu/detail";
    }

    @GetMapping("/list") // 메뉴 전체 조회하기
    public String findAllMenu(Model model, @PageableDefault Pageable pageable) {
        // 페이징 처리하지 않은 findAll();
//        List<MenuDTO> menuList = menuService.findeMenuList();
//        model.addAttribute("menus", menuList);

        // 매개 변수로 담는 pageable에 뭐가 담겨 있을까 log
        log.info("pageable : {}", pageable);

        Page<MenuDTO> menuList = menuService.findMenuListByPaging(pageable);

        log.info("조회한 내용 목록 : {}", menuList.getContent());
        log.info("총 페이지 수 : {}", menuList.getTotalPages());
        log.info("총 메뉴의 수 : {}", menuList.getTotalElements());
        log.info("해당 페이지에 표현될 요소의 수 : {}", menuList.getSize());
        log.info("첫 페이지 여부 : {}", menuList.isFirst());
        log.info("마지막 페이지 여부 : {}", menuList.isLast());
        log.info("정렬 방식 : {}", menuList.getSort());
        log.info("여러 페이지 중 현재 인덱스 : {}", menuList.getNumber());

        PagingButton pagingButton = Pagenation.getPagingInfo(menuList);

        model.addAttribute("menus", menuList);
        model.addAttribute("paging", pagingButton);
        return "menu/list";
    }

    @GetMapping("/querymethod")
    public void queryMethodPage() { // void로 할 시 요청 주소 자체가 view 페이지 주소가 된다.

    }

    @GetMapping("/search")
    public String findByMenuPrice(@RequestParam int menuPrice, Model model) {
        List<MenuDTO> menuList = menuService.findByMenuPrice(menuPrice);

        model.addAttribute("menuList", menuList);
        model.addAttribute("price", menuPrice); // 화면에서 넘어온 가격도 다시 넘겨줌

        return "menu/searchResult";
    }

    @GetMapping("/regist")
    public void registPagfe(){}

    @GetMapping(value = "/category" , produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<CategoryDTO> findCategoryList(){
        return menuService.findAllCategory(); // 카테고리 정보 반환하는 애
    }

//    public String asd (@ModelAttribute MenuDTO menuDTO) {
//
//    }
}
