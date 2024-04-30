package com.example.qltv.controller;

import org.springframework.web.bind.annotation.GetMapping;
import com.example.qltv.service.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    
    
    @GetMapping("/")
    public String listMember(Model model) {
        model.addAttribute("members", memberService.getAllMember());
        return "login";         
    }
}