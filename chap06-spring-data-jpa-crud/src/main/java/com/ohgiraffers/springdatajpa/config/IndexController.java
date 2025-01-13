package com.ohgiraffers.springdatajpa.config;

// static에 index가 아니라 templates에 있으면 못 읽어서 설정

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String indexPage() {
        return "main/main";
    }
}
