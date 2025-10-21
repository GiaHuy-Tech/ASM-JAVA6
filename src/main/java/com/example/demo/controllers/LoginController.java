package com.example.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private HttpSession session;

    // PasswordEncoder đã được xóa bỏ

    @GetMapping("/login")
    public String showLoginForm() {
        return "client/login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam("email") String email, 
                               @RequestParam("password") String password,
                               Model model) {
        
        Optional<Account> optionalAccount = accountRepo.findByEmail(email);

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            
            // So sánh mật khẩu trực tiếp (không an toàn)
            if (password.equals(account.getPassword())) {
                session.setAttribute("account", account);
                return "redirect:/"; // Chuyển hướng về trang chủ
            }
        }
        
        model.addAttribute("error", "Email hoặc mật khẩu không đúng.");
        return "client/login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa session để đăng xuất
        return "redirect:/";  // Chuyển hướng về trang chủ
    }
}