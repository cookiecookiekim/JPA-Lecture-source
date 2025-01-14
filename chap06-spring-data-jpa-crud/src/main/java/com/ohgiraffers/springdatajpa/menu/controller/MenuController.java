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
    public void registPagfe() {
    }

    @GetMapping("/regist2")
    public void registPagfe2() {
    }

    @GetMapping(value = "/category", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<CategoryDTO> findCategoryList() {
        // return 구문이 view 지정하는 것이 아닌, Data를 리턴한다
        return menuService.findAllCategory(); // 카테고리 정보 반환하는 애
    }

    @PostMapping("/regist")
    public String newMenuRegist(@ModelAttribute MenuDTO newMenu) {
        System.out.println("view에서 전달받은 newMenu = " + newMenu);

        menuService.registNewMenu(newMenu);

        return "redirect:/menu/list"; // @GetMapping("/list") // 메뉴 전체 조회하기
        // 얘한테 다시 요청 보내기
    }

    @PostMapping("/regist2") // 정수 반환받아보고 싶어서 해봤는데 완전 비효율적. 캇트
    public String newMenuRegist2(@ModelAttribute MenuDTO newMenu) {

        System.out.println("newMenu = " + newMenu);
        int result = menuService.registNewMenu2(newMenu);

        if (result >= 1) {
            return "menu/regist2";
        } else {
            return "main/main";
        }
    }

    @GetMapping("/modify")
    public void modifyPage() {
    }

    @PostMapping("/modify")
    public String modifyMenu(@ModelAttribute MenuDTO modifyMenu) {
        System.out.println("수정할 정보 modifyMenu 잘 넘어오는지 = " + modifyMenu);
        menuService.modifyMenu(modifyMenu);

        return "redirect:/menu/" + modifyMenu.getMenuCode();
        // 수정되면 url이 menu/수정 코드 → @GetMapping("/{menuCode}") 이쪽 페이지로 가게
    }

    @GetMapping("/delete")
    public void deletePage() {
    }

    @PostMapping("/delete")
    public String deleteMenu(@RequestParam int menuCode) {
        menuService.deleteMenu(menuCode);

        return "redirect:/menu/list";
    }
}
