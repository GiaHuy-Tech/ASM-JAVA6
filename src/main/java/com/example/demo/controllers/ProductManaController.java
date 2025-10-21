package com.example.demo.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Products;
import com.example.demo.repository.ProductRepository;

import jakarta.validation.Valid;

@Controller
public class ProductManaController {

    @Autowired
    ProductRepository productRepo;

    // ✅ Trang quản lý sản phẩm
    @GetMapping("/product-mana")
    public String productList(Model model) {
        List<Products> list = productRepo.findAll();
        model.addAttribute("list", list);
        model.addAttribute("product", new Products());
        return "admin/productMana";
    }

    // ✅ Thêm sản phẩm
    @PostMapping("/product-mana/add")
    public String addProduct(@Valid @ModelAttribute("product") Products product, 
                             BindingResult result, 
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("list", productRepo.findAll());
            return "admin/productMana";
        }
        product.setCreatedDate(new Date());
        productRepo.save(product);
        return "redirect:/product-mana";
    }

    // ✅ Sửa sản phẩm (hiển thị form)
    @GetMapping("/product-mana/edit/{id}")
    public String editForm(@PathVariable("id") Integer id, Model model) {
        Optional<Products> p = productRepo.findById(id);
        if (p.isPresent()) {
            model.addAttribute("product", p.get());
        } else {
            model.addAttribute("product", new Products());
        }
        model.addAttribute("list", productRepo.findAll());
        return "admin/productMana";
    }

    // ✅ Cập nhật sản phẩm
    @PostMapping("/product-mana/update")
    public String updateProduct(@Valid @ModelAttribute("product") Products product,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("list", productRepo.findAll());
            return "admin/productMana";
        }
        productRepo.save(product);
        return "redirect:/product-mana";
    }

    // ✅ Xóa sản phẩm
    @GetMapping("/product-mana/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        productRepo.deleteById(id);
        return "redirect:/product-mana";
    }
}
