package ua.kpi.ist.springlab1.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.ist.springlab1.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}