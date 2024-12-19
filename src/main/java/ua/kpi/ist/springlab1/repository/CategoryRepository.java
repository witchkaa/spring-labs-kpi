package ua.kpi.ist.springlab1.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.ist.springlab1.model.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    @Query(value = """
        SELECT
            c.id AS categoryId,
            c.name AS categoryName,
            p.id AS productId,
            p.name AS productName
        FROM
            categories c
        LEFT JOIN
            products p ON c.id = p.category_id
    """, nativeQuery = true)
    List<Object[]> getCategoryProductRelations();

    List<Category> findByNameContainingIgnoreCase(String name);

    List<Category> findByName(String name);
}