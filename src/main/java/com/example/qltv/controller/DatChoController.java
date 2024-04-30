package com.example.qltv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DatChoController {
    @GetMapping("/datcho")
    public String loadDatCho(Model model) {
        return "datcho";
    }
  
}

