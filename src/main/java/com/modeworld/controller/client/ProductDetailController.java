package com.modeworld.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductDetailController {
	@GetMapping("/product-detail")
    public String productDetail() {
        return "client/product-detail";
    }
}