/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.qltv.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.qltv.DTO.MemberDTO;
import com.example.qltv.entity.Member;
import com.example.qltv.repository.MemberRepository;
import com.example.qltv.service.Impl.MemberNotFoundException;
import com.example.qltv.service.Impl.MemberServiceImpl;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.bytebuddy.utility.RandomString;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberServiceImpl memberService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("member", new Member());
        return "login";
    }

    @PostMapping("/login")
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

            return "/index";
        } else {
            return "redirect:/login?error";
        }
    }

    // trang đăng ký
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("member", new Member());
        return "/signup";
    }

    // trang đăng ký thành công
    @PostMapping("/process_register")
    public String saveMember(@ModelAttribute MemberDTO memberDTO) {
        Member member = memberService.addMember(memberDTO);
        if (member != null) {
            return "redirect:/login";
        } else {
            return "redirect:/signup";
        }
    }

    // trang quên mật khẩu
    @GetMapping(value = { "/forgotPassword" })
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("pageTitle", "Forgot Password");
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPasswordForm(HttpServletRequest request, Model model) {
        String email = request.getParameter("Email");
        String token = RandomString.make(45);

        // System.out.println("Email: " + email);
        // System.out.println("Token: " + token);

        try {
            memberService.updateResetPasswordToken(token, email);

            String resetPasswordLink = memberService.getSiteURL(request) + "/resetPassword?token=" + token;
            memberService.sendEmail(email, resetPasswordLink);
            model.addAttribute("mesage", "we have sent a reset password link to your email.");
        } catch (MemberNotFoundException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error while sending email.");
        }
        model.addAttribute("pageTitle", "Forgot Password");
        return "forgotPassword";
    }

    @GetMapping("/resetPassword")
    public String showResetPasswordForm(@Param("token") String token, Model model) {
        Member member = memberService.get(token);
        if (member == null) {
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid token");
            return "message";
        }
        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "Reset your Password");
        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String processResetPasswordForm(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("newPassword");
        Member member = memberService.get(token);
        if (member == null) {
            model.addAttribute("title", "Reset your password");
            model.addAttribute("message", "Invalid token");

        } else {
            memberService.updatePassword(member, password);
            model.addAttribute("message", "Change password successfully");
        }
        return "message";
    }
}
