package ua.kpi.ist.springlab1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kpi.ist.springlab1.model.Category;
import ua.kpi.ist.springlab1.model.Product;
import ua.kpi.ist.springlab1.service.CategoryProductDto;
import ua.kpi.ist.springlab1.service.CategoryService;

import java.util.Collections;
import java.util.List;

@Tag(name = "Categories", description = "Operations related to categories")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(
            summary = "Add products to category",
            description = "This method adds a list of products to an existing category.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Products added successfully"),
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "500", description = "An error occurred while adding products")
            }
    )
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
            System.out.println(e.getCause());
            return ResponseEntity.status(500).body("An error occurred while adding products to category");
        }
    }

    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories.")
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @Operation(
            summary = "Get category by ID",
            description = "Retrieve a category by its unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category found"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Add a new category",
            description = "Create a new category and return its generated ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category added successfully")
            }
    )
    @PostMapping
    public ResponseEntity<Long> addCategory(@RequestBody Category category) {
        Category c = categoryService.addCategory(category);
        return ResponseEntity.ok(c.getId());
    }
    @Operation(
            summary = "Search categories by name",
            description = "Search categories by their name.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categories found")
            }
    )
    @GetMapping("/search")
    public List<Category> searchCategories(@RequestParam String name) {
        return categoryService.searchCategories(name);
    }
    @GetMapping("/find")
    public ResponseEntity<List<Category>> findCategory(@RequestParam String name) {
        List<Category> categories = categoryService.findByName(name);
        if (categories.isEmpty()) {
            return ResponseEntity.status(404).body(Collections.emptyList());
        }
        return ResponseEntity.ok(categories);
    }
    @Operation(
            summary = "Update a category",
            description = "Update the details of an existing category by its ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        category.setId(id);
        categoryService.updateCategory(category);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Delete category",
            description = "Delete an existing category by its ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get all category-product relations",
            description = "Retrieve a list of all categories with their associated products."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved category-product relations"),
            @ApiResponse(responseCode = "500", description = "An error occurred while retrieving the relations")
    })
    @GetMapping("/products")
    public ResponseEntity<List<CategoryProductDto>> getCategoryProductRelations() {
        List<CategoryProductDto> relations = categoryService.getCategoryProductRelations();
        return ResponseEntity.ok(relations);
    }
}