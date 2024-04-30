package com.example.qltv.service.Impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.qltv.entity.Member;
import com.example.qltv.repository.MemberRepository;
import com.example.qltv.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
    private MemberRepository memberRepository ;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    @Override 
    public List<Member> getAllMember()
    {
        return memberRepository.findAll();
    }
    
}

