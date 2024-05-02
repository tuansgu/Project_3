package com.example.qltv.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.qltv.entity.Thietbi;
import com.example.qltv.service.ThietBiService;

@RestController
public class ThietBiController {
    private final ThietBiService thietBiService;
    @Autowired
    public ThietBiController(ThietBiService thietBiService) {
        this.thietBiService = thietBiService;
    }
   

    @GetMapping("/thietbis")
    public List<Thietbi> getAllThietBi() {
        return thietBiService.getAllThietBi();
    }
}
