package ua.kpi.ist.springlab1.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Category {
    private String name;
    private final List<Product> products = new ArrayList<>();
    private final List<Category> subcategories = new ArrayList<>();
    private Category parentCategory;
    public Category(String name) {
        this.name = name;
    }

    public void addSubcategory(Category subcategory) {
        subcategories.add(subcategory);
        subcategory.setParentCategory(this);
    }
    public void addProduct(Product product) {
        products.add(product);
    }
    public void removeProductByName(String productName) {
        products.removeIf(product -> product.getName().equals(productName));
    }
    public void removeSubcategoryByName(String subcategoryName) {
        subcategories.removeIf(subcategory -> subcategory.getName().equals(subcategoryName));
    }
}