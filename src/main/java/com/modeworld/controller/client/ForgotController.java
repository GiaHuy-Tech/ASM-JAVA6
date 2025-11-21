package com.modeworld.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForgotController {
	@GetMapping("/forgot-password")
    public String forgot() {
        return "client/forgot-password";
    }
}
