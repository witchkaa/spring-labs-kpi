package ua.kpi.ist.springlab1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.kpi.ist.springlab1.model.Category;
import ua.kpi.ist.springlab1.model.Product;
import ua.kpi.ist.springlab1.service.CatalogService;

@Controller
public class CatalogController {

    // @Autowired
    private CatalogService catalogService;

    @Autowired
    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", catalogService.getAllCategories());
        return "index";
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam String categoryName) {
        catalogService.addCategory(new Category(categoryName));
        return "redirect:/";
    }

    @PostMapping("/addSubcategory")
    public String addSubcategory(@RequestParam String parentCategoryName, @RequestParam String subcategoryName) {
        catalogService.addSubcategory(parentCategoryName, new Category(subcategoryName));
        return "redirect:/";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam String categoryName, @RequestParam String productName,
                             @RequestParam String description, @RequestParam double price) {
        catalogService.addProduct(categoryName, new Product(productName, description, price));
        return "redirect:/";
    }
}