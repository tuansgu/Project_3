/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.qltv.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.qltv.DTO.LoginDTO;
import com.example.qltv.entity.Member;
import com.example.qltv.repository.MemberRepository;
import com.example.qltv.response.LoginMesage;
import com.example.qltv.service.Impl.MemberServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@CrossOrigin
@RequestMapping("/")
public class UserController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberServiceImpl memberService;

    @GetMapping("")
    public String showLoginForm(Model model) {
        model.addAttribute("member", new Member());
        return "login";
    }

    // @PostMapping("/")
    // public String processLogin(@RequestParam("MaTV") int maTV,
    // @RequestParam("password") String password,
    // RedirectAttributes redirectAttributes, Member member) {
    // if (maTV == member.getMaTV() && password.equals(member.getPassword())) {
    // // Xác thực thành công, chuyển hướng đến trang chính
    // return "/index";
    // } else {
    // // Xác thực thất bại, hiển thị thông báo lỗi và chuyển hướng lại trang đăng
    // nhập
    // redirectAttributes.addFlashAttribute("error", "Invalid maTV or password");
    // return "/";
    // }
    // }

    // trang đăng ký
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("member", new Member());
        return "/signup";
    }

    // trang đăng ký thành công
    @PostMapping("/process_register")
    public String postMethodName(Member member) {
        // memberRepository.save(member);
        memberService.addMember(member);
        return "registerSuccess";
    }
    // @PostMapping(path = "/process_register")
    // public String saveMember(@RequestBody Member member) {
    // memberService.addMember(member);
    // return "registerSuccess";
    // }

    @PostMapping(path = "/")
    public String loginMember(@RequestParam int MaTV, @RequestParam String Password, HttpServletResponse response)
            throws UnsupportedEncodingException {
        Optional<Member> optionalMember = memberRepository.findOneByIdAndPassword(MaTV, Password);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            String hoten = member.getHoTen();

            // Lưu thông tin tên thành viên vào cookie
            String encodedValue = URLEncoder.encode(hoten, "UTF-8");
            Cookie cookie = new Cookie("hoten", encodedValue);
            cookie.setMaxAge(3600); // Thời gian sống của cookie (1 giờ)
            response.addCookie(cookie);

            return "index";
        } else {
            return "/";
        }
    }

    // trang quên mật khẩu
    @GetMapping(value = { "/forgotPassword" })
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("member", new Member());
        return "/forgotPassword";
    }
}
