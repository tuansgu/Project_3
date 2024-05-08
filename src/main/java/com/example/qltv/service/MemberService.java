package com.example.qltv.service;

import com.example.qltv.DTO.LoginDTO;
import com.example.qltv.entity.Member;
import com.example.qltv.response.LoginMesage;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    // public List<Member> getAllMember();

    // public Member login(int mssv, String password);

    // public boolean logout(String email);

    public Member addMember(Member member);

    // public boolean updatePassword(String email, String password);

    // public boolean isEmailExist(String email);

    // public boolean isIdExist(int id);

    // public Optional<Member> findByEmal(String email);

    // LoginMesage loginMember(LoginDTO loginDTO);
}