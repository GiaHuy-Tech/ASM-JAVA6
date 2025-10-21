package com.example.demo.controllers;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Account;
import com.example.demo.model.Orders;
import com.example.demo.repository.OrdersRepository;
import com.example.demo.repository.AccountRepository;

@Controller
@RequestMapping("/oders-mana")
public class OrdersManaController {

    @Autowired
    OrdersRepository ordersRepo;

    @Autowired
    AccountRepository accountRepo;

    // ✅ Danh sách đơn hàng
    @GetMapping
    public String list(Model model) {
        model.addAttribute("ordersList", ordersRepo.findAll());
        model.addAttribute("order", new Orders());
        model.addAttribute("accounts", accountRepo.findAll());
        return "admin/oders-mana";
    }

    // ✅ Form thêm mới
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("order", new Orders());
        model.addAttribute("ordersList", ordersRepo.findAll());
        model.addAttribute("accounts", accountRepo.findAll());
        return "admin/oders-mana";
    }

    // ✅ Thêm mới đơn hàng
    @PostMapping("/add")
    public String add(
            @RequestParam("accountId") Integer accountId,
            @ModelAttribute("order") Orders order) {

        Account acc = accountRepo.findById(accountId).orElse(null);
        order.setAccountId(acc);
        order.setCreatedDate(new Date());
        ordersRepo.save(order);
        return "redirect:/oders-mana";
    }

    // ✅ Form sửa
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Orders order = ordersRepo.findById(id).orElse(new Orders());
        model.addAttribute("order", order);
        model.addAttribute("ordersList", ordersRepo.findAll());
        model.addAttribute("accounts", accountRepo.findAll());
        return "admin/oders-mana";
    }

    // ✅ Cập nhật đơn hàng
    @PostMapping("/update")
    public String update(
            @RequestParam("accountId") Integer accountId,
            @ModelAttribute("order") Orders order) {

        Account acc = accountRepo.findById(accountId).orElse(null);
        order.setAccountId(acc);
        ordersRepo.save(order);
        return "redirect:/oders-mana";
    }

    // ✅ Xóa đơn hàng
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        ordersRepo.deleteById(id);
        return "redirect:/oders-mana";
    }
}
