package ua.kpi.ist.springlab1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.ist.springlab1.model.Category;
import ua.kpi.ist.springlab1.model.Product;
import ua.kpi.ist.springlab1.repository.CategoryProductDto;
import ua.kpi.ist.springlab1.repository.CategoryRepository;
import ua.kpi.ist.springlab1.repository.ProductRepository;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> searchCategories(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> findByName(String description) {
        return categoryRepository.findByName(description);
    }

    @Transactional
    public void addProductsToCategory(Long categoryId, List<Product> products) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        for (Product product : products) {
            product.setCategory(category);
            productRepository.save(product);
            category.getProducts().add(product);
        }

        categoryRepository.save(category);
    }

    public List<CategoryProductDto> getCategoryProductRelations() {
        return categoryRepository.getCategoryProductRelations();
    }
}
