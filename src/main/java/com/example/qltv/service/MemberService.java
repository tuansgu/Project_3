package com.example.qltv.service;

import com.example.qltv.DTO.MemberDTO;
import com.example.qltv.entity.Member;
import com.example.qltv.service.Impl.MemberNotFoundException;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    public Optional<Member> login(int maTV, String password);

    public Member addMember(MemberDTO memberDTO);

    public void sendEmail(String email, String resetPasswordLink)
            throws UnsupportedEncodingException, MessagingException;

    public void updateResetPasswordToken(String token, String email) throws MemberNotFoundException;

    public Member get(String resetPasswordToken);

    public void updatePassword(Member member, String newPassword);

    public String getSiteURL(HttpServletRequest request);
}