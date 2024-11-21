package ua.kpi.ist.springlab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kpi.ist.springlab1.model.Category;
import ua.kpi.ist.springlab1.service.CatalogService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CatalogService catalogService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        catalogService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String nameFilter) {
        List<Category> categories = catalogService.getAllCategories();

        if (nameFilter != null) {
            categories = categories.stream()
                    .filter(cat -> cat.getName().toLowerCase().contains(nameFilter.toLowerCase()))
                    .collect(Collectors.toList());
        }

        int start = Math.min(page * size, categories.size());
        int end = Math.min((page + 1) * size, categories.size());

        return ResponseEntity.ok(categories.subList(start, end));
    }

    @GetMapping("/{name}")
    public ResponseEntity<Category> getCategory(@PathVariable String name) {
        return catalogService.findCategoryByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{name}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable String name,
            @RequestBody Category updatedCategory) {
        Optional<Category> categoryOpt = catalogService.findCategoryByName(name);

        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            category.setName(updatedCategory.getName());
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        Optional<Category> categoryOpt = catalogService.findCategoryByName(name);

        if (categoryOpt.isPresent()) {
            catalogService.deleteCategory(name);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
