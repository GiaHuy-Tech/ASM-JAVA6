	package com.example.demo.controllers;
	
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;  
	import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.repository.CategoryRepository;
	
	



@Controller
public class HomeController {

    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        return "client/index"; // Trang giao diện chính
    }
}
