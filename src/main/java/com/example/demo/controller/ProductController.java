package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

/**
 * Controller for product management
 */
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    /**
     * Lists all products
     * GET /products
     */
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAllEntities());
        model.addAttribute("titulo", "Product List");
        return "products/list";
    }

    /**
     * Shows product details
     * GET /products/1
     */
    @GetMapping("/{id}")
    public String showProduct(@PathVariable UUID id, Model model) {
        Product product = productService.findByIdEntity(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        model.addAttribute("product", product);
        model.addAttribute("titulo", "Product Details");
        return "products/detail";
    }

    /**
     * Shows form to create product
     * GET /products/new
     */
    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAllEntities());
        model.addAttribute("titulo", "New Product");
        return "products/form";
    }

    /**
     * Processes product creation
     * POST /products
     */
    @PostMapping
    public String createProduct(
            @Valid @ModelAttribute Product product,
            BindingResult result,
            RedirectAttributes flash,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "New Product");
            return "products/form";
        }

        productService.saveEntity(product);
        flash.addFlashAttribute("success", "Product created successfully");
        return "redirect:/products";
    }

    /**
     * Shows form to edit product
     * GET /products/1/edit
     */
    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable UUID id, Model model) {
        Product product = productService.findByIdEntity(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAllEntities());
        model.addAttribute("titulo", "Edit Product");
        return "products/form";
    }

    /**
     * Processes product update
     * POST /products/1
     */
    @PostMapping("/{id}")
    public String updateProduct(
            @PathVariable UUID id,
            @Valid @ModelAttribute Product product,
            BindingResult result,
            RedirectAttributes flash,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Edit Product");
            return "products/form";
        }

        product.setId(id);
        productService.saveEntity(product);
        flash.addFlashAttribute("success", "Product updated successfully");
        return "redirect:/products";
    }

    /**
     * Deletes a product
     * POST /products/1/delete
     */
    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable UUID id, RedirectAttributes flash) {
        productService.deleteById(id);
        flash.addFlashAttribute("success", "Product deleted successfully");
        return "redirect:/products";
    }
}
