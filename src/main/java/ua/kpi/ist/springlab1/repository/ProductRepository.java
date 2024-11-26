package ua.kpi.ist.springlab1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ua.kpi.ist.springlab1.model.Product;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

    TreeMap<Long, Product> products = new TreeMap<>();
    public Page<Product> findAll(Pageable pageable, String name, String description, Double minPrice, Double maxPrice) {
        List<Product> productList = new ArrayList<>(products.values());

        if (name != null) {
            productList.removeIf(p -> !p.getName().toLowerCase().contains(name.toLowerCase()));
        }
        if (description != null) {
            productList.removeIf(p -> !p.getDescription().toLowerCase().contains(description.toLowerCase()));
        }
        if (minPrice != null) {
            productList.removeIf(p -> p.getPrice() < minPrice);
        }
        if (maxPrice != null) {
            productList.removeIf(p -> p.getPrice() > maxPrice);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), productList.size());
        return new PageImpl<>(productList.subList(start, end), pageable, productList.size());
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
