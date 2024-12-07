package ua.kpi.ist.springlab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kpi.ist.springlab1.model.Category;
import ua.kpi.ist.springlab1.model.Product;
import ua.kpi.ist.springlab1.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<String> addProductsToCategory(
            @PathVariable Long categoryId,
            @RequestBody List<Product> products) {

        try {
            categoryService.addProductsToCategory(categoryId, products);
            return ResponseEntity.ok("Products added successfully to category");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Category not found");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while adding products to category");
        }
    }
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Long> addCategory(@RequestBody Category category) {
        Long generatedId = categoryService.addCategory(category);
        return ResponseEntity.ok(generatedId);
    }
    @GetMapping("/search")
    public List<Category> searchCategories(@RequestParam String name) {
        return categoryService.searchCategories(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        category.setId(id);
        categoryService.updateCategory(category);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}