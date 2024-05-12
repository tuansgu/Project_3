package com.example.qltv.controller;

import org.springframework.web.bind.annotation.GetMapping;

import com.example.qltv.entity.Thietbi;
import com.example.qltv.service.MemberService;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// private MemberService memberService;
// public HomeController(MemberService memberService) {
//     this.memberService = memberService;
// }

@Controller
public class HomeController {
    @GetMapping("/")
    public String Home() {
        return "index";
    }
}
