package ua.kpi.ist.springlab1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.ist.springlab1.model.Category;
import ua.kpi.ist.springlab1.model.Product;
import ua.kpi.ist.springlab1.repository.CatalogRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CatalogService {
    private final CatalogRepository catalogRepository;


    public List<Category> getAllCategories() {
        return catalogRepository.findAllCategories();
    }

    public void addCategory(Category category) {
        catalogRepository.saveCategory(category);
    }

    public Optional<Category> findCategoryByName(String name) {
        return findCategoryRecursively(name, catalogRepository.findAllCategories());
    }
    private Optional<Category> findCategoryRecursively(String name, List<Category> categories) {
        for (Category category : categories) {
            if (category.getName().equals(name)) {
                return Optional.of(category);
            }
            Optional<Category> subcategory = findCategoryRecursively(name, category.getSubcategories());
            if (subcategory.isPresent()) {
                return subcategory;
            }
        }
        return Optional.empty();
    }
    public void addSubcategory(String parentCategoryName, Category subcategory) {
        findCategoryByName(parentCategoryName).ifPresent(parentCategory -> parentCategory.addSubcategory(subcategory));
    }

    public void addProduct(String categoryName, Product product) {
        findCategoryByName(categoryName).ifPresent(category -> category.addProduct(product));
    }
}