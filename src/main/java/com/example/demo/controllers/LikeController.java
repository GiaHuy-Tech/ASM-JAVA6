package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LikeController {

	    @GetMapping("/wishlist")
	    public String wishlist() {
	        // Tự động tìm file index.html trong thư mục templates
	        return "client/wishlist";
	    }
}
