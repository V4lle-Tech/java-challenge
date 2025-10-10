package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

/**
 * Controller for category management
 */
@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Lists all categories
     * GET /categories
     */
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.findAllEntities());
        model.addAttribute("titulo", "Category List");
        return "categories/list";
    }

    /**
     * Shows category details
     * GET /categories/1
     */
    @GetMapping("/{id}")
    public String showCategory(@PathVariable UUID id, Model model) {
        Category category = categoryService.findByIdEntity(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        model.addAttribute("category", category);
        model.addAttribute("titulo", "Category Details");
        return "categories/detail";
    }

    /**
     * Shows form to create category
     * GET /categories/new
     */
    @GetMapping("/new")
    public String newCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("titulo", "New Category");
        return "categories/form";
    }

    /**
     * Processes category creation
     * POST /categories
     */
    @PostMapping
    public String createCategory(
            @Valid @ModelAttribute Category category,
            BindingResult result,
            RedirectAttributes flash,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "New Category");
            return "categories/form";
        }

        categoryService.saveEntity(category);
        flash.addFlashAttribute("success", "Category created successfully");
        return "redirect:/categories";
    }

    /**
     * Shows form to edit category
     * GET /categories/1/edit
     */
    @GetMapping("/{id}/edit")
    public String editCategoryForm(@PathVariable UUID id, Model model) {
        Category category = categoryService.findByIdEntity(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        model.addAttribute("category", category);
        model.addAttribute("titulo", "Edit Category");
        return "categories/form";
    }

    /**
     * Processes category update
     * POST /categories/1
     */
    @PostMapping("/{id}")
    public String updateCategory(
            @PathVariable UUID id,
            @Valid @ModelAttribute Category category,
            BindingResult result,
            RedirectAttributes flash,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Edit Category");
            return "categories/form";
        }

        category.setId(id);
        categoryService.saveEntity(category);
        flash.addFlashAttribute("success", "Category updated successfully");
        return "redirect:/categories";
    }

    /**
     * Deletes a category
     * POST /categories/1/delete
     */
    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable UUID id, RedirectAttributes flash) {
        categoryService.deleteById(id);
        flash.addFlashAttribute("success", "Category deleted successfully");
        return "redirect:/categories";
    }
}