package com.modeworld.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrdersController {
	@GetMapping("/orders")
    public String orders() {
        return "client/orders";
    }
}
