package ua.kpi.ist.springlab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.kpi.ist.springlab1.model.Category;
import ua.kpi.ist.springlab1.service.CategoryProductDto;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT new ua.kpi.ist.springlab1.service.CategoryProductDto(c.id, c.name, p.id, p.name) " +
            "FROM Category c JOIN c.products p")
    List<CategoryProductDto> getCategoryProductRelations();

    List<Category> findByNameContainingIgnoreCase(String name);

    List<Category> findByName(String description);
}