package com.example.qltv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.qltv.entity.Thietbi;
import com.example.qltv.repository.ThietBiRepository;
@Service
public class ThietBiService {
    private ThietBiRepository thietbiRepository;

    @Autowired
    public ThietBiService(ThietBiRepository thietbiRepository) {
        this.thietbiRepository = thietbiRepository;
    }
    public List<Thietbi> getAllThietBi(){
        System.out.println("hello"+thietbiRepository.findAll());
        return thietbiRepository.findAll();
    }
}
