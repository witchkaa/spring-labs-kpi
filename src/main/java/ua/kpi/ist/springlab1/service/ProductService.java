package ua.kpi.ist.springlab1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.kpi.ist.springlab1.dao.ProductDao;
import ua.kpi.ist.springlab1.model.Product;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productDao.findById(id);
    }

    public int addProduct(Product product) {
        return productDao.create(product);
    }

    public void updateProduct(Product product) {
        productDao.update(product);
    }

    public void deleteProduct(Long id) {
        productDao.delete(id);
    }
}