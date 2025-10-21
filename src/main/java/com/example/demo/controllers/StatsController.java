	package com.example.demo.controllers;
	
	import org.springframework.stereotype.Controller; 
	import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
	
	

@Controller
public class StatsController {

    @GetMapping("/stats")
    public String product() {
        // Tự động tìm file index.html trong thư mục templates
        return "admin/stats";
    }
}