package com.example.qltv.service;

import com.example.qltv.entity.Member;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    List<Member> getAllMember();
}