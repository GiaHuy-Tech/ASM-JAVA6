package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.model.Products;

import java.util.List;

@Controller	
public class HomeController {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ProductRepository productsRepo;

	@GetMapping("/")
	public String home(Model model) {
		// Lấy tất cả danh mục
		List<Products> featured = productsRepo.findByAvailableTrue();
		model.addAttribute("featuredProducts", featured);
		model.addAttribute("categories", categoryRepo.findAll());
		// Lấy 8 sản phẩm mới nhất
		List<Products> featuredProducts = productsRepo.findAllByOrderByIdDesc();
		if (featuredProducts.size() > 8) {
			featuredProducts = featuredProducts.subList(0, 8);
		}
		model.addAttribute("featuredProducts", featuredProducts);
		return "client/index";

	}

}