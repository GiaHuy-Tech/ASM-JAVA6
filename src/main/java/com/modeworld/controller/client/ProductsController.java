package com.modeworld.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductsController {
	@GetMapping("/products")
    public String product() {
        return "client/products";
    }
}
