package ua.kpi.ist.springlab1.repository;

import org.springframework.stereotype.Repository;
import ua.kpi.ist.springlab1.model.Category;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CatalogRepository {
    private final List<Category> categories = new ArrayList<>();

    public List<Category> findAllCategories() {
        return categories;
    }

    public void saveCategory(Category category) {
        categories.add(category);
    }

    public void deleteCategory(String categoryName) {
        categories.removeIf(category -> category.getName().equals(categoryName));
    }
}