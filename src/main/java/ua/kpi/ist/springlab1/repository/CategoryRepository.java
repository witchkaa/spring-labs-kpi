package ua.kpi.ist.springlab1.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.ist.springlab1.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    @Query("SELECT new ua.kpi.ist.springlab1.repository.CategoryProductDto(c.id, c.name, p.id, p.name) FROM Category c JOIN c.products p")
    List<CategoryProductDto> getCategoryProductRelations();

    List<Category> findByNameContainingIgnoreCase(String name);

    List<Category> findByName(String description);
}