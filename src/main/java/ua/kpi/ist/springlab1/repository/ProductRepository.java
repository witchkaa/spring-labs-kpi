package ua.kpi.ist.springlab1.repository;

import org.springframework.stereotype.Repository;
import ua.kpi.ist.springlab1.model.Product;

import java.util.*;

@Repository
public class ProductRepository {

    TreeMap<Long, Product> products = new TreeMap<>();
    public Iterable<Product> findAll() {
        return products.values();
    }

    public Product findById(Long id) {
        return products.get(id);
    }

    public Product save(Product product) {
        if(product.getId() == null) {
            Long id = products.lastKey() + 1;
            product = new Product(id, product.getName(), product.getDescription(), product.getPrice());
        }
        products.put(product.getId(), product);
        return product;
    }

    public void deleteById(Long id) {
        products.remove(id);
    }

    public ProductRepository() {
        products.put(1L, new Product(1L, "Pink shirt", "Cotton", 100));
        products.put(2L, new Product(2L, "Red dress", "For summer", 180));
        products.put(3L, new Product(3L, "Black shorts", "For workout", 80));
    }
}
