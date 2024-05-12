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

import com.example.qltv.entity.Member;
import com.example.qltv.entity.Xuly;
import com.example.qltv.entity.Thongtinsd;
import com.example.qltv.repository.MemberRepository;
import com.example.qltv.repository.XuLyRepository;
import com.example.qltv.repository.ThongTinSDRepository;
import com.example.qltv.service.Impl.MemberNotFoundException;
import com.example.qltv.service.Impl.MemberServiceImpl;
import jakarta.servlet.http.HttpSession;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.bytebuddy.utility.RandomString;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;
    private XuLyRepository xuLyRepository;
    private ThongTinSDRepository thongTinSDRepository;
    @Autowired
    private MemberServiceImpl memberService;

    public MemberController(MemberServiceImpl memberService, MemberRepository memBerRepository, XuLyRepository xuLyRepository, ThongTinSDRepository thongTinSDRepository) {
        this.memberService = memberService;
        this.memberRepository = memBerRepository;
        this.xuLyRepository = xuLyRepository;
        this.thongTinSDRepository = thongTinSDRepository;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("member", new Member());
        return "login";
    }

    @PostMapping("/login")
    public String loginMember(@RequestParam int MaTV, @RequestParam String Password, HttpServletResponse response, HttpSession session, RedirectAttributes redirectAttributes)
            throws UnsupportedEncodingException {
        Optional<Member> optionalMember = memberRepository.findOneByIdAndPassword(MaTV, Password);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            String hoten = member.getHoTen();
            session.setAttribute("loggedInMaTV", MaTV);
            // Lưu thông tin tên thành viên vào cookie
            String encodedValue = URLEncoder.encode(hoten, "UTF-8");
            Cookie cookie = new Cookie("hoten", encodedValue);
            cookie.setMaxAge(3600); // Thời gian sống của cookie (1 giờ)
            response.addCookie(cookie);
            redirectAttributes.addFlashAttribute("member", member);

            return "redirect:/index";
        } else {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/index")
    public String indexAfterLogin(@ModelAttribute("member") Member member, Model model) {
        model.addAttribute("member", member);
        return "index";
    }

    @GetMapping("/hosothanhvien")
    public String showProfile(Model model, @RequestParam("maTV") int maTV) {
        Member tv = memberRepository.findById(maTV).orElse(null);;
        if (tv != null) {
            model.addAttribute("member", tv);   
        }

        return "hosothanhvien";
    }

    @GetMapping("/trangthaivipham")
    public String trangthaivppage(Model model, @RequestParam("maTV") String maTV) {
        try {
            int maThanhVien = Integer.parseInt(maTV);
            Member tv = new Member();
            tv.setMaTV(maThanhVien);

            Member tv2 = memberRepository.findById(maThanhVien).orElse(null);;
            List<Xuly> xuLyList = xuLyRepository.findByThanhVien(tv);

            if (!xuLyList.isEmpty()) {
                model.addAttribute("xuLyList", xuLyList);
                model.addAttribute("member", tv2);
                return "trangthaivipham";
            } else {
                model.addAttribute("errorMessage", "Không tìm thấy thông tin xử lý cho thành viên có mã " + maTV);
                model.addAttribute("member", tv2);
                return "trangthaivipham";
            }
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Mã thành viên không hợp lệ: " + maTV);
            return "index";
        }
    }

    @GetMapping("/thietbidangmuon")
    public String tbdangmuonpage(Model model, @RequestParam("maTV") String maTV) {
        if (maTV != null && !maTV.isEmpty()) {
            try {
                int maTVInt = Integer.parseInt(maTV);
                Member tv = new Member();
                tv.setMaTV(maTVInt);
                Member tv3 = memberRepository.findById(maTVInt).orElse(null);;

                List<Thongtinsd> userList = thongTinSDRepository.findByThanhVien(tv);
                if (!userList.isEmpty()) {
                    model.addAttribute("thongTinList", userList);
                    model.addAttribute("member", tv3);

                    return "thietbidangmuon";
                } else {
                    model.addAttribute("member", tv3);
                    return "thietbidangmuon";
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid maTV format: " + maTV);
            }
        }
        return "thietbidangmuon";
    }

    @GetMapping("/datchothietbi")
    public String datchotbpage(Model model, @RequestParam("maTV") String maTV) {
        if (maTV != null && !maTV.isEmpty()) {
            try {
                int maTVInt = Integer.parseInt(maTV);
                Member tv = new Member();
                tv.setMaTV(maTVInt);
                Member tv3 = memberRepository.findById(maTVInt).orElse(null);;

                List<Thongtinsd> userList = thongTinSDRepository.findByThanhVien(tv);
                if (!userList.isEmpty()) {
                    model.addAttribute("thongTinList", userList);
                    model.addAttribute("member", tv3);

                    return "datchothietbi";
                } else {
                    model.addAttribute("member", tv3);
                    return "datchothietbi";
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid maTV format: " + maTV);
            }
        }
        return "datchothietbi";
    }

    @GetMapping("/doimatkhau")
    public String showResetPassword(Model model, @RequestParam("maTV") String maTV) {
        int maThanhVien = Integer.parseInt(maTV);

        Member tv2 = memberRepository.findById(maThanhVien).orElse(null);;
        model.addAttribute("member", tv2);

        return "doimatkhau";
    }


    @PostMapping("/processResetPassword")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmNewPassword") String confirmNewPassword,
            Model model, HttpSession session) {

        int maTV = (int) session.getAttribute("loggedInMaTV");

        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("error", "Mật khẩu mới không khớp.");
            Member tv = memberRepository.findById(maTV).orElse(null);;
            model.addAttribute("member", tv);
            return "doimatkhau";
        }

        if (maTV != 0) {
            boolean passwordChanged = memberService.changePassword(maTV, currentPassword, newPassword);
            if (passwordChanged) {
                Member tv2 = memberRepository.findById(maTV).orElse(null);;
                model.addAttribute("member", tv2);
                model.addAttribute("successMessage", "Thay đổi mật khẩu thành công.");

                return "doimatkhau"; 
            } else {
                Member tv3 = memberRepository.findById(maTV).orElse(null);;
                model.addAttribute("member", tv3);
                model.addAttribute("error", "Mật khẩu hiện tại không đúng.");
                return "doimatkhau";
            }
        }

        Member tv4 = memberRepository.findById(maTV).orElse(null);;
        model.addAttribute("member", tv4);
        return "doimatkhau";
    }


    // trang đăng ký
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("member", new Member());
        return "/signup";
    }

    // trang đăng ký thành công
    @PostMapping("/process_register")
    public String saveMember(@ModelAttribute Member memberDTO) {
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
