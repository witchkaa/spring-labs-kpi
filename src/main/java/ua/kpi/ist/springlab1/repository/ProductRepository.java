package ua.kpi.ist.springlab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kpi.ist.springlab1.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}