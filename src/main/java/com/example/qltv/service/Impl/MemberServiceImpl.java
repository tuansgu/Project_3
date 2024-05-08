package com.example.qltv.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.qltv.DTO.LoginDTO;
import com.example.qltv.entity.Member;
import com.example.qltv.repository.MemberRepository;
import com.example.qltv.response.LoginMesage;
import com.example.qltv.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // @Override
    // public List<Member> getAllMember() {
    // return memberRepository.findAll();
    // }

    // @Override
    // public Member login(int mssv, String password) {
    // return null;
    // }

    // @Override
    // public boolean logout(String email) {
    // return false;
    // }

    @Override
    public Member addMember(Member member) {
        member = new Member(member.getMaTV(), member.getHoTen(), member.getKhoa(),
                member.getNganh(), member.getSDT(),
                member.getEmail(), member.getPassword());
        memberRepository.save(member);
        return member;
    }

    // @Override
    // public boolean updatePassword(String email, String password) {
    // return false;
    // }

    // @Override
    // public boolean isEmailExist(String email) {
    // return false;
    // }

    // @Override
    // public boolean isIdExist(int id) {
    // return false;
    // }

    // @Override
    // public Optional<Member> findByEmal(String email) {
    // return null;
    // }

    // @Override
    // public LoginMesage loginMember(LoginDTO loginDTO) {
    // String msg = "";
    // Member member1 = new Member();
    // member1 = memberRepository.findById(loginDTO.getMaTV());
    // if (member1 != null) {
    // String password = loginDTO.getPassword();
    // String encodedPassword = member1.getPassword();
    // Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
    // if (isPwdRight) {
    // Optional<Member> member2 =
    // memberRepository.findOneByIdAndPassword(loginDTO.getMaTV(), encodedPassword);
    // if (member2.isPresent()) {
    // return new LoginMesage("Login Success", true);
    // } else {
    // return new LoginMesage("Login Failed", false);
    // }
    // } else {
    // return new LoginMesage("password Not Match", false);
    // }
    // } else {
    // return new LoginMesage("Email not exits", false);
    // }
    // }

}
