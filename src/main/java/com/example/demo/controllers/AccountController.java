package com.example.demo.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private HttpSession session;

	// Trang thông tin tài khoản
	@GetMapping("/account")
	public String accountPage(Model model) {
		Account acc = (Account) session.getAttribute("account");
		if (acc == null) {
			return "redirect:/login";
		}
		model.addAttribute("account", acc);
		return "client/account";
	}

	// Cập nhật họ tên
	@PostMapping("/account/update-fullname")
	public String updateFullName(@RequestParam("fullName") String fullName) {
		Account acc = (Account) session.getAttribute("account");
		if (acc != null) {
			acc.setFullName(fullName);
			accountRepo.save(acc);
			session.setAttribute("account", acc);
		}
		return "redirect:/account";
	}

	// Cập nhật địa chỉ
	@PostMapping("/account/update-address")
	public String updateAddress(@RequestParam("address") String address) {
		Account acc = (Account) session.getAttribute("account");
		if (acc != null) {
			acc.setAddress(address);
			accountRepo.save(acc);
			session.setAttribute("account", acc);
		}
		return "redirect:/account";
	}

	// Cập nhật số điện thoại
	@PostMapping("/account/update-phone")
	public String updatePhone(@RequestParam("phone") String phone) {
		Account acc = (Account) session.getAttribute("account");
		if (acc != null) {
			acc.setPhone(phone);
			accountRepo.save(acc);
			session.setAttribute("account", acc);
		}
		return "redirect:/account";
	}

	@PostMapping("/account/update-password")
	public String updatePassword(@RequestParam("password") String password) {
		Account acc = (Account) session.getAttribute("account");
		if (acc != null) {
			acc.setPassword(password);
			accountRepo.save(acc);
			session.setAttribute("account", acc);
		}
		return "redirect:/account";
	}

	// Cập nhật email
	@PostMapping("/account/update-email")
	public String updateEmail(@RequestParam("email") String email) {
		Account acc = (Account) session.getAttribute("account");
		if (acc != null) {
			acc.setEmail(email);
			accountRepo.save(acc);
			session.setAttribute("account", acc);
		}
		return "redirect:/account";
	}

	// Upload avatar
	@PostMapping("/account/upload-avatar")
	public String uploadAvatar(@RequestParam("avatar") MultipartFile file) {
		Account acc = (Account) session.getAttribute("account");
		if (acc != null && !file.isEmpty()) {
			try {
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				Path uploadDir = Paths.get("uploads/avatar/");
				if (!Files.exists(uploadDir)) {
					Files.createDirectories(uploadDir);
				}
				Path filePath = uploadDir.resolve(fileName);
				Files.write(filePath, file.getBytes());
				acc.setPhoto("/images/avatar/" + fileName);
				accountRepo.save(acc);
				session.setAttribute("account", acc);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/account";
	}

}
