package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Handles GET request to root "/"
     *
     * @param model Model to pass data to view
     * @return View name (without .html)
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("titulo", "Order Management");
        model.addAttribute("mensaje", "Welcome to the order management application.");
        model.addAttribute("fecha", LocalDateTime.now());

        return "index";
    }

    /**
     * Handles GET request to "/about"
     *
     * @param model Model to pass data to view
     * @return View name (without .html)
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("titulo", "About");
        model.addAttribute("descripcion", "Application information.");
        return "about";
    }
}
