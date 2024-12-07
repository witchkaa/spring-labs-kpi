package ua.kpi.ist.springlab1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.ist.springlab1.dao.CategoryDao;
import ua.kpi.ist.springlab1.dao.ProductDao;
import ua.kpi.ist.springlab1.model.Category;
import ua.kpi.ist.springlab1.model.Product;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ProductDao productDao;
    private final CategoryDao categoryDao;

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryDao.findById(id);
    }
    public List<Category> searchCategories(String name) {
        return categoryDao.searchCategories(name);
    }

    public Long addCategory(Category category) {
        return categoryDao.create(category);
    }

    public void updateCategory(Category category) {
        categoryDao.update(category);
    }

    public void deleteCategory(Long id) {
        categoryDao.delete(id);
    }

    @Transactional
    public void addProductsToCategory(Long categoryId, List<Product> products) {
        for (Product product : products) {
            Long productId = (long) productDao.create(product);

            categoryDao.linkProductToCategory(categoryId, productId);
        }
    }
    public List<CategoryProductDto> getCategoryProductRelations() {
        return categoryDao.getCategoryProductRelations();
    }
}