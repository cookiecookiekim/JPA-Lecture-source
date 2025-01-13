package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.model.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
// 필드 주입보다 생성자 주입이 안전한데 그때 사용하는 유용한 어노테이션
@RequiredArgsConstructor // 필드에 final 키워드가 붙으면 자동으로 생성자 주입 해줌
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
}
